package se.chimps.green.cm

import java.util.{ServiceLoader, UUID}

import akka.actor.{ActorSystem, Address}
import akka.cluster.Cluster
import se.chimps.green.api.system.PropertiesDiscovery
import se.chimps.green.spi.Discovery

import scala.concurrent.ExecutionContext

class ComputeModule {
	val system = ActorSystem("Green")
	val id = UUID.randomUUID().toString

	private val service = "ComputeModule"

	import scala.collection.JavaConverters._
	private val discoveryLoader = ServiceLoader.load(classOf[Discovery])
		.iterator().asScala ++ Seq(new PropertiesDiscovery())

	def start():Unit = {
		val cluster = Cluster(system)

		if (cluster.settings.SeedNodes.isEmpty) {
			val clusterNodes = discoveryLoader.flatMap(d => d.lookup(service))

			if (clusterNodes.nonEmpty) {
				clusterNodes
  				.foreach(a => {
					  val addr = Address("akka.tcp", "Green", a._1, a._2)
					  cluster.join(addr)
				  })
			} else {
				cluster.join(cluster.selfAddress)
			}

			discoveryLoader.foreach(l =>
				l.register(
					id,
					service,
					cluster.selfAddress.host.get,
					cluster.selfAddress.port.get))
		}

	}

	def stop():Unit = {
		discoveryLoader.foreach(d => d.deregister(id))
		val cluster = Cluster(system)
		cluster.leave(cluster.selfAddress)
		system.terminate()
  		.map(_ => println("ActorSystem was shutdown."))(ExecutionContext.global)
	}
}

package se.chimps.green.cm

import scala.sys.ShutdownHookThread

object Start {
	def main(args:Array[String]):Unit = {
		if (args.nonEmpty) {
			System.setProperty("akka.remote.netty.tcp.bind-hostname", System.getenv("HOSTNAME"))
			System.setProperty("akka.remote.netty.tcp.hostname", args(0))
		}

		val cm = new ComputeModule()
		cm.start()

		ShutdownHookThread {
			cm.stop()
		}
	}
}

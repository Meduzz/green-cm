package se.chimps.green.cm

import scala.sys.ShutdownHookThread

object Start {
	def main(args:Array[String]):Unit = {
		if (args.nonEmpty) {
			System.setProperty("akka.remote.netty.tcp.bind-hostname", System.getenv("HOSTNAME"))
			System.setProperty("akka.remote.netty.tcp.bind-port", "2552")

			if (args(0).contains(":")) {
				val Array(host:String, port:String) = args(0).split(":")
				System.setProperty("akka.remote.netty.tcp.hostname", host)
				System.setProperty("akka.remote.netty.tcp.port", port)
			} else {
				System.setProperty("akka.remote.netty.tcp.hostname", args(0))
			}
		}

		val cm = new ComputeModule()
		cm.start()

		ShutdownHookThread {
			cm.stop()
		}
	}
}

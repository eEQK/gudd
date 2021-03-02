import cynorkis.coordinator.CoordinatorFactory
import cynorkis.core.ConnectionRequest
import kotlin.system.exitProcess

fun main() {
    CoordinatorFactory.basic()
        .send(ConnectionRequest("GET", "https://www.reddit.com/r/kotlin.json"))
        .thenAccept {
            println("${it.statusCode} ${it.statusCodePhrase}")
            exitProcess(0)
        }
}
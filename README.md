# Chocor
Minecraft plugin library for Kotlin users
코틀린 유저용 라이브러리

```kt

// in JavaPlugin

listener(PlayerTeleportEvent::event) {
  isCancelled = true
}

loopTask {
  logger.log("Hello World!")
}

pluginMessage("highright:is_idiot", plugin) {
  player.sendMessage(text("that's fact :)")
}

```

```kt

// in others

listener(PlayerTeleportEvent::event, plugin) {
  isCancelled = true
}

loopTask(plugin) {
  logger.log("Hello World!")
}

pluginMessage("highright:is_idiot", plugin) {
  player.sendMessage(text("that's fact :)")
}

```

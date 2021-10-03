# Chocor
Minecraft plugin library for Kotlin users

it can use this with compileOnly(if you apply Chocor plugin in server) or with FatJar

---

코틀린 유저용 마인크래프트 플러그인 라이브러리

compileOnly로 사용할수도 있고(서버에 초커 플러그인을 넣는다면) FatJar로도 사용이 가능합니다

---

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

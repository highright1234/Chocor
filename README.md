# Chocor
Minecraft plugin library for Kotlin users

```kt
listener(PlayerTeleportEvent::event) {
  isCancelled = true
} // in JavaPlugin
```
```kt
listener(PlayerTeleportEvent::event, plugin) {
  isCancelled = true
} // in others
```

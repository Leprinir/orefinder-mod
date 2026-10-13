# COORDINATION.md — Логи координации агентов

> Каждый агент пишет сюда свой статус после работы. Формат: `[agent-NAME]: действие → результат`

---

## Репозиторий

**https://github.com/Leprinir/orefinder-mod** (аккаунт: Leprinir)

Owner: Leprinir | Repo: public | GitHub Actions CI: ✅ активен

---

## Логи

### 2026-10-13

**21:15** — `[orchestrator]`: Инициализация репозитория. Создана структура папок, README, TASKS, COORDINATION.
**21:16** — `[orchestrator]**: Завершено: build.gradle, settings.gradle, gradle.properties, gradlew.bat, gradle-wrapper.jar
**21:17** — `[orchestrator]**: Завершено: TASK-02 OreFinderMod.java, TASK-03 ModItems.java + OreTypes.java
**21:18** — `[orchestrator]**: Завершено: TASK-04 HudOverlay.java, TASK-05 OreFinderEventHandler.java
**21:19** — `[orchestrator]**: Завершено: все 5 рецептов крафта (TASK-06), локализация en/ru (TASK-10)
**21:20** — `[orchestrator]**: Завершено: модели предметов, CI/CD GitHub Actions, текстуры-заглушки (TASK-11, TASK-12, TASK-15)

## Остаточные задачи для агентов

- [ ] TASK-08: Звуковые эффекты (нужно записать/найти .ogg файлы)
- [ ] TASK-09: Частицы при сканировании (ParticleEmitter)
- [ ] TASK-13: Конфиг файл (OreFinderConfig)
- [ ] TASK-14: Иконка мода (mod-icon.png 128x128)

## Статус выполнения

| Задача | Статус | Исполнитель |
|--------|--------|-------------|
| TASK-01 build.gradle | ✅ | orchestrator |
| TASK-02 OreFinderMod.java | ✅ | orchestrator |
| TASK-03 ModItems + OreTypes | ✅ | orchestrator |
| TASK-04 HudOverlay | ✅ | orchestrator |
| TASK-05 EventHandler | ✅ | orchestrator |
| TASK-06 Рецепты | ✅ | orchestrator |
| TASK-10 Локализация | ✅ | orchestrator |
| TASK-11 Текстуры | ✅ | orchestrator |
| TASK-12 Модели | ✅ | orchestrator |
| TASK-15 CI/CD | ✅ | orchestrator |
| TASK-07 Апгрейд | ⏸️ объединено с TASK-06 | — |
| TASK-08 Звуки | 🔴 | — |
| TASK-09 Частицы | 🔴 | — |
| TASK-13 Конфиг | 🔴 | — |
| TASK-14 Иконка | 🔴 | — |

---

## Архитектура решения

### Основные классы

```
com.orefinder.mod
├── OreFinderMod.java          ← главный класс (init, events)
├── item/
│   ├── OreFinderItem.java     ← базовый предмет с логикой сканирования
│   └── UpgradeItem.java       ← предмет для апгрейда
├── registry/
│   ├── ModItems.java          ← регистрация всех предметов
│   └── OreTypes.java          ← enum руд и их свойств
└── client/
    └── HudOverlay.java        ← отрисовка HUD на экране
```

### OreTypes enum

```java
public enum OreTypes {
    COAL(Blocks.COAL_ORE, "coal", 5),
    COPPER(Blocks.COPPER_ORE, "copper", 10),
    IRON(Blocks.IRON_ORE, "iron", 15),
    GOLD(Blocks.GOLD_ORE, "gold", 20),
    LAPIS(Blocks.LAPIS_ORE, "lapis", 20),
    REDSTONE(Blocks.REDSTONE_ORE, "redstone", 20),
    EMERALD(Blocks.EMERALD_ORE, "emerald", 30),
    DIAMOND(Blocks.DIAMOND_ORE, "diamond", 50),
    NETHER_GOLD(Blocks.NETHER_GOLD_ORE, "nether_gold", 5),
    NETHER_QUARTZ(Blocks.NETHER_QUARTZ_ORE, "nether_quartz", 10),
    DEBRIS(Blocks.ANCIENT_DEBRIS, "netherite", 100);
}
```

### Радиусы сканирования по уровням

| Уровень | Предмет | Радиус |
|---------|---------|--------|
| 1 | stone_ore_finder | 8 |
| 2 | iron_ore_finder | 16 |
| 3 | gold_ore_finder | 32 |
| 4 | diamond_ore_finder | 64 |
| 5 | emerald_master_finder | 128 |

---

## Следующие шаги

1. `agent-coder` → TASK-01: build.gradle, settings.gradle, gradle wrapper
2. `agent-coder` → TASK-02: OreFinderMod.java
3. `agent-coder` → TASK-03: ModItems.java, OreTypes.java
4. Параллельно: `agent-assets` → TASK-10 (локализация) и TASK-11 (текстуры)

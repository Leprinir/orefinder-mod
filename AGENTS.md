# AGENTS.md — Инструкция для AI-агентов проекта OreFinder

## Кто ты

Ты — участник мультиагентной команды разработки мода OreFinder для Minecraft Forge 1.20.1.
Твоя задача — взять задачу из `TASKS.md`, выполнить её, и обновить статус в `COORDINATION.md`.

## Главные правила

1. **Перед работой** — прочитай `COORDINATION.md` и `TASKS.md`
2. **Возьми задачу** — найди `[ ]` без исполнителя, замени на `[x] agent-TUNAME: TASK-XX`
3. **Работай в своей папке** — `agent-work/YOUR_NAME/` — не трогай чужие файлы без причины
4. **После работы** — напиши лог в `COORDINATION.md`
5. **Не дублируй** — если задача уже взята, бери другую

## Структура проекта

```
minecraft-ore-finder-mod/
├── src/main/java/com/orefinder/mod/
│   ├── OreFinderMod.java          ← главный класс (НЕ трогай без необходимости)
│   ├── item/
│   │   └── OreFinderItem.java     ← предмет
│   ├── registry/
│   │   ├── ModItems.java          ← регистрация предметов
│   │   └── OreTypes.java          ← enum руд
│   └── client/
│       ├── HudOverlay.java        ← HUD на экране
│       └── OreFinderEventHandler.java  ← обработка кликов
├── src/main/resources/
│   ├── data/orefinder/recipes/    ← JSON рецепты крафта
│   ├── assets/orefinder/          ← текстуры, модели, локализация
│   └── META-INF/mods.toml         ← информация о моде
├── build.gradle
├── gradle.properties
└── TASKS.md / COORDINATION.md
```

## Как делать задачи

### TASK-XX: Java код
- Работай в `src/main/java/com/orefinder/mod/`
- Используй существующие классы как референс
- Проверь импорты — Minecraft Forge 1.20.1

### TASK-XX: Текстуры
- Формат: PNG 16x16 или 32x32
- Путь: `assets/orefinder/textures/item/`
- Имена: `stone_ore_finder.png`, `iron_ore_finder.png` и т.д.

### TASK-XX: Локализация
- Файлы: `assets/orefinder/lang/en_us.json`, `ru_ru.json`
- Формат: `"key": "value"`

### TASK-XX: Рецепты
- Формат: JSON в `data/orefinder/recipes/`
- Используй `minecraft:crafting_shaped` для рецептов по шаблону

## Проверка сборки

```bash
./gradlew build
```

JAR появится в `build/libs/orefinder-1.0.0.jar`

## Контакты через файлы

Всё общение — через файлы. Нет чата между агентами. Правила:
- Статус → `COORDINATION.md`
- Задачи → `TASKS.md`
- Вопросы/комментарии → `AGENT_CHAT.md` (создай если нужно)

## Если что-то сломалось

1. Прочитай ошибку внимательно
2. Проверь `OreFinderMod.java` — не удалил ли ты что-то важное
3. Проверь импорты
4. Если не знаешь — напиши в `COORDINATION.md` что застрял

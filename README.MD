# InGameTitleManager



## About
InGameTitleManager (IGTM) - This is a simple mod that does only one thing - changes the location of the title (including the subtitle as well)

## How to use
First of all, you need to set up key assignments!

Go to `Options... > Controls... > Key Binds...`
Next, find the InGameTitleManager category and assign all the keys

![Image of key binds](https://github.com/Beengoo/inGameTitleManager/blob/master/images/keybinds_example.png)

After that, go to the server or singleplayer and press the action key for title (or subtitle if you want) and the move button, you will see a preview of where it will be located!


## Build from source
Make sure you have installed java 21!
Clone repository and build!

```bash
git clone https://github.com/Beengoo/inGameTitleManager.git
cd inGameTitleManager
.\gradlew clean build
```

mod will located in `{projectDirectory}/build/libs/inGameTitleManager-{version}.jar`!

![](https://raw.githubusercontent.com/logeFox/JavaLang/master/img/JavaLangLib.png)

<div align="center">

## JavaLang
_A simple i18n Text Handler_

[![](https://jitpack.io/v/logeFox/JavaLang.svg)](https://jitpack.io/#logeFox/JavaLang) [![](https://img.shields.io/badge/Static%20Docs-Home-red)](https://logefox.github.io/JavaLang/WorkInProgressJavaLang) ![GitHub](https://img.shields.io/github/license/logeFox/JavaLang)
</div>

## Index

- [About](#about-javalang)
- [Features](#features)
- [Installation](#installation-of-dependencies)
- [Usage](#usage-and-examples)
	- [Basic Usage](#basic-usage)
	- [Interpolation](#interpolation-usage)
	- [Emoji](#emoji-usage)
- [Static Documentation](https://logefox.github.io/JavaLang/WorkInProgressJavaLang)


## About JavaLang
JavaLang or Java **Language** is a text manager, with the aim of improving the developer's *experience* for cleaner code, and for the translator to *manage* translations in a single place and in better order.
It is very **important** to know that this software does not have any automatic translation system (like google translate), but simply *manages access* to text using **unique** keys.

## Features
- Support for 135 languages. [All languages available](https://logefox.github.io/JavaLang/Languages)
- Emoji support based on **emojipedia**, check [here](https://logefox.github.io/JavaLang/WorkInProgressJavaLang) to see how to implement it
- Translations storage based on **JSON** files

## Installation of Dependencies
To access **JavaLang** integrate the Java SDK into your project.

- For *Maven* Add the JitPack repository and the dependencies into the `pom.xml` file:
	```xml
	<repositories>
		<repository>
			<id>jitpack.io</id>
			<url>https://jitpack.io</url>
		</repository>
	</repositories>
	```
	```xml
	<dependency>
		<groupId>com.github.logeFox</groupId>
		<artifactId>JavaLang</artifactId>
		<version>1.0</version>
	</dependency>
	```
- For *Gradle* Add the JitPack repository in your root `build.gradle` at the end of repositories, and add the dependency as shown:
	```groovy
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
	```
	```groovy
	dependencies {
			implementation 'com.github.logeFox:JavaLang:1.0'
	}
	```

## Usage and Examples
To start using **JavaLang**, we need to create the translation files and then access them via our instance.

### Setup the Translation Files
1. To start with, you need to create the *root directory* for containing the translation files, its place in the `Resources Folders` of the java project.
2. Create the translation files in the *root directory* created earlier, with the file name as the supported languages (such as `en_US.json`, `en_IT.json`, etc). You can check the available languages [here](https://logefox.github.io/JavaLang/Languages). 
	Below is an example of how to create the translation file containment structure
	```
	Resources
		└─ translations
			├─ it_IT.json
			└─ en_US.json
	```
1. Add sentences and translations to the translation files as shown below:
	```json
	{
		"hello": "Ciao!",
		"introduceMySelf": "Piacere mi presento, sono {}."
	}
	```
	
	```json
	{
		"hello": "Hello!",
		"introduceMySelf": "Pleased to meet you, I am {}."
	}
	```

## Instantiating and using JavaLang
For more informations and examples read the [Documentation](https://logefox.github.io/JavaLang/WorkInProgressJavaLang).

### Make Istance
To create an instance of JavaLang, you will need to indicate the _root folder_ in the resources, and indicate the language via the *Languages enumerator* or *String*.
```java
JavaLang javalang = new JavaLang("translations", Languages.it_IT);
```

 ### Basic usage
To access the phrases simply use the `JavaLang.getLang()` function with its corresponding key.
 ```java
JavaLang javalang = new JavaLang("translations", Languages.it_IT);  
javalang.getLang("hello"); // Output: Ciao!
```
 
If you want to load a new locale, you can use the `JavaLang.reload()` function with the language enumerator or string name of the language you want.
```java
JavaLang javalang = new JavaLang("translations", Languages.it_IT);  

javalang.reload(Languages.en_US);

javalang.getLang("hello"); // Output: Hello!
```

**Hot reload** this functionality allows the translation file to be modified in real time and by executing the `JavaLang.reload()` function the new values can be accessed without restarting the current application. Although limited, this functionality is still in WIP.

### Interpolation usage
To implement text within translations, it will only be necessary to *pass arguments* in the order of the specific fields for text input, which is represented by the character sequence `{}`.
There are no limits in passing data to the `JavaLang.getLang("key", ...)` function, only the need to have the same amount of data and `{}`.
```java
JavaLang javalang = new JavaLang("translations", Languages.it_IT);  
javalang.getLang("intorduceMySelf", "loge"); // Output: Piacere mi presento, sono loge.
```

### Emoji usage
The *emoji management* system in JavaLang is characterised by the possibility of being able to use emoji customised to the service you are using, or to have the emoji inserted by the service itself.
In fact, JavaLang will insert unicodes only when the name of the emoji is indicated all in uppercase and that follows the nomenclature of [emojipedia](https://emojipedia.org/).
The next example shows how to use the JavaLang integrated system.
 ```json
{
	"hello": "Ciao! :WAVING_HAND:"
}
```

 ```java
JavaLang javalang = new JavaLang("translations", Languages.it_IT);  
javalang.getLang("hello"); 
// Output in UTF-8: 			Ciao! 👋
// Output in classic console:   Ciao! \U0001f44b
```

If you want to use emoji from a particular service such as **Discord**, the system will not take into consideration emoji that do not follow the format explained above, allowing the target application to process the emoji code.
The next example is applied for a possible application for Discord.
 ```json
{
	"hello": "Ciao! :wave:"
}
```

 ```java
JavaLang javalang = new JavaLang("translations", Languages.it_IT);  
javalang.getLang("hello"); // Output in classic console: Ciao! :wave:
```

## Licence
JavaLang is open-source under [MIT licence](https://github.com/logeFox/JavaLang/blob/master/LICENSE)

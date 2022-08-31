
<div align="center">

## JTranslation
_A simple i18n Text Handler_

[![](https://jitpack.io/v/logeFox/JavaLang.svg)](https://jitpack.io/#logeFox/JavaLang) ![GitHub](https://img.shields.io/github/license/logeFox/JTranslation)
</div>

## Index

- [About](#about-JTranslation)
- [Features](#features)
- [Installation](#installation-of-dependencies)
- [Usage](#usage-and-examples)
	- [Basic Usage](#basic-usage)
	- [Interpolation](#interpolation-usage)
	- [Emoji](#emoji-usage)


## About JTranslation
JTranslation or Java **Language** is a text manager, with the aim of improving the developer's *experience* for cleaner code, and for the translator to *manage* translations in a single place and in better order.
It is very **important** to know that this software does not have any automatic translation system (like google translate), but simply *manages access* to text using **unique** keys.

## Features
- Support for 135 languages. [All languages available](https://github.com/logeFox/JTranslation/blob/master/languages.md)
- Emoji support based on **emojipedia**, check [here](#Emoji usage) to see how to implement it
- Translations storage based on **JSON** files

## Installation of Dependencies
To access **JTranslation** integrate the Java SDK into your project.

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
		<artifactId>JTranslation</artifactId>
		<version>1.1</version>
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
			implementation 'com.github.logeFox:JTranslation:1.0'
	}
	```

## Usage and Examples
To start using **JTranslation**, we need to create the translation files and then access them via our instance.

### Setup the Translation Files
1. To start with, you need to create the *root directory* for containing the translation files, its place in the `Resources Folders` of the java project.
2. Create the translation files in the *root directory* created earlier, with the file name as the supported languages (such as `en_US.json`, `it_IT.json`, etc). You can check the available languages [here](https://logefox.github.io/JTranslation/Languages). 
	Below is an example of how to create the translation file containment structure
	```
	Resources
		└─ translations
			├─ it_IT.json
			└─ en_US.json
	```
3. Add sentences and translations to the translation files as shown below:
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
**note:** JTranslation has a system of checking the *JSON keys*, when they are loaded to avoid errors in the use of different translation files via `#getLang()` or `#getLangWithLocale()`, the keys are *checked* for the ***same compatibility***.

## Instantiating and using JTranslation
For more informations and examples read the [Documentation](https://logefox.github.io/JTranslation/WorkInProgressJTranslation).

### Make Istance
To create an instance of JTranslation, you must point to the _root directory_ in the resources using the `#setRoot()` in the `JTranslationBuilder`, if your *root directory* is called "translations" you don't need to point to it, but you must point to the language using either the *Languages* enumerator or the *Strings* in the `JTranslationBuilder()` constructor.

```java
JTranslation JTranslation = new JTranslationBuilder(Languages.it_IT, Languages.en_US).build();
```

 ### Basic usage
To access the phrases just use the `JTranslation#getLang()` function with its corresponding key, if *multiple locales* are loaded with this method only the translations of the **first language** loaded will be taken.

```java
JTranslation.getLang("hello"); // Output: Ciao!
```

If I want to select the locale to get the translations from, I can use the `#getLangWithLocale()` method.

```java
JTranslation.getLangWithLocale("en_US", "hello"); // Output: Hello!
```
 
If you want to load new locales, you can use the `JTranslation#reload()` function with the language enumerator or string name of the language you want.

```java
JTranslation.reload(Languages.en_US);

JTranslation.getLang("hello"); // Output: Hello!
```

**Hot reload** this functionality allows the translation file to be modified in real time and by executing the `JTranslation#reload()` function the new values can be accessed without restarting the current application.

#### Remove locale
To remove locales, the `#removeLocale()` method is available, which will also perform a `reload` after the removal.

```java
JTranslation.removeLocale("en_US");
```

### Interpolation usage
To implement text within translations, it will only be necessary to *pass arguments* in the order of the specific fields for text input, which is represented by the character sequence `{}`.
There are no limits in passing data to the `JTranslation#getLang("key", ...)` function, only the need to have the same amount of data and `{}`.
```java 
JTranslation.getLang("intorduceMySelf", "Ale"); // Output: Piacere mi presento, sono Ale.
```

### Emoji usage
The *emoji management* system in JTranslation is characterised by the possibility of being able to use emoji customised to the service you are using, or to have the emoji inserted by the service itself.
In fact, JTranslation will insert unicodes only when the name of the emoji is indicated all in uppercase and that follows the nomenclature of [emojipedia](https://emojipedia.org/).
The next example shows how to use the JTranslation integrated system.
 ```json
{
	"hello": "Ciao! :WAVING_HAND:"
}
```

 ```java
JTranslation JTranslation = new JTranslationBuilder(Languages.it_IT).build();

JTranslation.getLang("hello"); 
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
JTranslation.getLang("hello"); // Output in classic console: Ciao! :wave:
```

## Licence
JTranslation is open-source under [MIT licence](https://github.com/logeFox/JTranslation/blob/master/LICENSE)

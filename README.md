# telegram-irc
[![Build Status](https://travis-ci.org/stevesoltys/telegram-irc.svg?branch=master)](https://travis-ci.org/stevesoltys/telegram-irc)

A bridge between [Telegram](https://telegram.org/) and [IRC](https://en.wikipedia.org/wiki/Internet_Relay_Chat).

## Features
* Mapping of Telegram groups to IRC channels.
* Mapping of Telegram users to IRC bots, which includes private messaging.
* Multi-user support. This means multiple IRC users can be mapped and relayed to their own Telegram bots.
* Quoted messages when a user replies to another user's message.
* Automatic uploading of images, videos, stickers, and other files to https://mixtape.moe by default.

## Requirements
* JDK 1.8
* IRCd

## Quick Start
1. Download the latest release from the [release page](https://github.com/stevesoltys/telegram-irc/releases).
2. Unzip the release and run `./gradlew build`
3. Copy the built application JAR to the installation path of your choosing: 
`cp build/libs/telegram-irc-0.2.0.jar ./telegram-irc.jar`
4. Copy the example YAML configuration file: 
`cp deploy/config.example.yml ./config.yml`
5. Create your Telegram bot and edit the default configuration using the
[configuration guide](https://github.com/stevesoltys/telegram-irc/wiki/Configuration): `$EDITOR config.yml`.
6. Start the application: `java -jar telegram-irc.jar  --spring.config.location=config.yml`

For a more detailed installation guide, see [this page](https://github.com/stevesoltys/telegram-irc/wiki/Installation).

## License
This application is available as open source under the terms of the [MIT License](http://opensource.org/licenses/MIT).

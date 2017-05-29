# telegram-irc
A Telegram <=> IRC gateway which aims for seamless integration.

## Features
* Mapping of Telegram groups to IRC channels.
* Mapping of Telegram users to IRC bots, which includes private messaging.
* Multi-user support. This means multiple IRC users can be mapped and relayed to their own Telegram bots.
* Quoted messages when a user replies to another user's message.
* Media files (images, stickers, etc) are not yet supported, but are a priority on the road map.

## Requirements
* JDK 1.8

## Quick Start
1. Set up an IRC server and configure it to allow an unlimited amount of connections from the telegram-irc host address.
2. Download the latest release from the [release page](https://github.com/stevesoltys/telegram-irc/releases).
3. Unzip the release and run `./gradlew build`
4. Copy the built application JAR to the installation path of your choosing: 
`cp build/libs/telegram-irc-0.1.0.jar /opt/telegram-irc/telegram-irc.jar`
5. Copy the example JSON configuration file to your home directory: 
`cp deploy/config.example.json ~/.config/telegram-irc/config.json`
6. Create your Telegram bot and edit the default config `$EDITOR ~/.config/telegram-irc/config.json` using the
[configuration guide](https://github.com/stevesoltys/telegram-irc/wiki/Configuration).
7. Start the application: `java -jar /opt/telegram-irc/telegram-irc.jar`

### Some notes:
* Mapped Telegram group channels will be created automatically by the operator bot when a message is received.
* When a user sends a message in a Telegram group, their IRC bot is created and will connect to the server, joining 
the channel and sending the message automatically.
* By default you will need to send a message in a Telegram group, then `/whois` the operator bot to see what channels
were joined. Then, you can map that channel ID to a channel name of your choosing in the configuration file.
* There is currently no persistence. If telegram-irc is restarted, all telegram user bots will be lost until they send
another message on Telegram.

If you need a more detailed installation guide for production use, see 
[this page](https://github.com/stevesoltys/telegram-irc/wiki/Installation).

## Contributing
Bug reports and pull requests are welcome on GitHub at https://github.com/stevesoltys/telegram-irc. This project is 
intended tobe a safe, welcoming space for collaboration, and contributors are expected to adhere to the
[Contributor Covenant](http://contributor-covenant.org) code of conduct.

## License
This application is available as open source under the terms of the [MIT License](http://opensource.org/licenses/MIT).

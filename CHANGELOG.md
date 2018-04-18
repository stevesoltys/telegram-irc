## [0.2.0] - 2018-04-17
### Added
- Example systemd unit.
- Support for IRC action messages.
- Support for an operator bot authenticating itself as an IRC operator. 
- Support for relaying images via automatic uploads to [Imgur](https://imgur.com).
- Support for Telegram highlight encoding by appending an '@' symbol before Telegram IRC bot usernames in messages.

### Fixed
- Make IRC bots use a single thread to prevent race conditions.

## [0.1.2] - 2017-06-02
### Added
- SSL configuration flag for IRC.

### Fixed
- Telegram messages with new line characters are no longer truncated on IRC.

## [0.1.1] - 2017-05-31
### Fixed
- Bug where all private messages from Telegram were relayed twice.

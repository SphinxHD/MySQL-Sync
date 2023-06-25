# MySQL-Sync
Sync the data of your Players between several servers for free with MySQL Sync and a MySQL/MongoDB Database.

You will need a MySQL or MongoDB Server and two or more servers on which you would like to sync the data of Players. It won't need ANY system on your Proxy.

If you need any help with this plugin please use the Districts Support Server: https://discord.gg/cBeRrF8Dut

## Features

MySQL-Sync the following things from the Players:
* Inventory
* EnderChest
* EXP
* GameMode
* Hunger
* Health
* Effect
* Advancements
* Statistics

## How to install
> ⚠ Installation requires MySQL or MariaDB or MongoDB and More than one Server!
1. Download MySQL-Sync plugin
2. Place into your plugins folder on all servers.
3. Restart your server (**Do not use /reload command!**)
4. Check `plugins/MySQL-Sync/config.yml` and edit to be what you want.
> Also you need to edit the MySQL credentials in the config.yml.
5. Restart your server. (or use `/sync reload`)
6. Done!
> If you like this plugin, Please write a review! <3

## How to update
You can do this by simply replacing the .jar file of the plugin, but to ensure a reliable and secure update, please follow the steps below.
1. Download the Version you wan't to update to.
2. Place into your plugins folder on all servers.
3. Delete older versions in all plugin folders.
4. Backup config files (just in case something does not work right.)
5. Restart your server. (or use `/sync reload`)
> (The Plugin will reload the Config so it will get all your pre configured values from the Version before, will delete the old config, create a new one and put all your old values into it.)
6. Check `plugins/MySQL-Sync/config.yml` and edit to be what you want.
> Also you need to edit the MySQL credentials in the config.yml.
7. Restart your server. (or use `/sync reload`)
8. Done!

## Found a bug?

If you find a bug, please report it at the following page:
* Districts Support Server: https://discord.gg/cBeRrF8Dut
* Github issues Page: https://github.com/SphinxHD/MySQL-Sync/issues

> Before reporting a Bug or ginving the Plugin a bad review, please check out our known Issues Page [here](https://github.com/SphinxHD/MySQL-Sync/issues). We will fix the Issues that are known in every new update.

## Stats Disclosure
This plugin utilizes a plugin metrics system, which means that the following information is collected and sent to bstats.org.

    * A unique identifier
    * The server's version of Java
    * Whether the server is in offline or online mode
    * The plugin's version
    * The server's version
    * The OS version/name and architecture
    * The core count for the CPU
    * The number of players online
    * The Metrics version

You can find our stats [here](https://bstats.org/plugin/bukkit/MySQL%20Sync/15003). 
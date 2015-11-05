# LAMP Deployment Script #
Automatically deploy web services using the LAMP model.

## Installation ##

You can install it by executing the following command:

```bash
git clone https://github.com/yuwen41200/lamp-deployment-script.git
chmod +x lamp-deployment-script/dep
sudo cp lamp-deployment-script/dep /usr/local/bin/
```

## Overview ##

To setup a new deployment configuration for your project, run `dep init`. It will:
+ Prompt you to input your username and password for your git repository.
+ Prompt you to input the URL of your git repository, production server, and test server.
+ Prompt you to input the project root path on your production server and test server.
+ Execute `git init` and `git remote add origin URL_OF_YOUR_REPOSITORY`.
+ Save these settings to `dep.cfg` and add this configuration file to `.gitignore`.

To push a new commit to your git repository, run `dep push git`. It will:
+ Add `dep.cfg` to `.gitignore` if it has not been added, then execute `git add --all`.
+ Prompt you to fill in the commit message, then execute `git commit -m "YOUR_COMMIT_MESSAGE"`.
+ Execute `git push origin YOUR_BRANCH` and fill in the username and password for you.

To upload your project to the production server, run `dep push prod`. It will:
+ Add a `.htaccess` file to the production server to deny access temporarily.
+ Remove all files in the project root directory of the production server.
+ Use `scp` to copy local files to the production server.
+ Load SQL queries in `run_once.sql` on the production server.
+ Remove the `.htaccess` file on the production server.

To upload your project to the test server, run `dep push test`. It will:
+ Remove all files in the project root directory of the test server.
+ Use `scp` to copy local files to the test server.
+ Load SQL queries in `run_once.sql` on the test server.

You can simply run `dep push`:
+ By default, it is an alias to `dep push test`.
+ You can change this to other commands by modifying `dep.cfg`.

To pull the latest commit from your git repository, run `dep pull git`. It will:
+ Execute `git pull`.

To download your project from the production server, run `dep pull prod`. It will:
+ Use `scp` to copy remote files on the production server.

To download your project from the production server, run `dep pull test`. It will:
+ Use `scp` to copy remote files on the test server.

You can simply run `dep pull`:
+ By default, it is an alias to `dep pull git`.
+ You can change this to other commands by modifying `dep.cfg`.

To show the manual of the command, run `dep help`. It will:
+ Print its usage.

## License ##
[The MIT License](https://raw.githubusercontent.com/yuwen41200/lamp-deployment-script/master/LICENSE)

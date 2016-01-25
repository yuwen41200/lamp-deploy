# LAMP Deployment Tool #

Automatically Deploy LAMP-Based Web Services

## Getting Started ##

You can install it by executing the following command:

```bash
git clone https://github.com/yuwen41200/lamp-deploy.git
sudo cp lamp-deploy/dep.jar /usr/local/bin/
echo "alias dep='java -jar /usr/local/bin/dep.jar'" >> ~/.bashrc
source ~/.bashrc
```

## Usage ##

1. Switch to your project root directory by `cd /path/to/my/proj/`.

2. Run `dep init` to setup a new configuration.

    * If directory `.git` does not exists, print an error message `INVALID_GIT_REPOSITORY`.
    * Prompt you to input the username, password, default branch for your git repository.
    * Prompt you to input the username, password, IP address for your production server.
    * Prompt you to input the project root path on your production server.
    * Store these settings into `.depcfg` and add this file to `.gitignore`.

3. Run `dep` to deploy your project.

    * If file `.depcfg` does not exists, print an error message `DEP_NOT_CONFIGURED`.
    * Add `.depcfg` to `.gitignore` if it has not been added, then execute `git add --all`.
    * Prompt you to fill in the commit message, then execute `git commit -m "MY_CMT_MSG"`.
    * Execute `git push -u origin YOUR_BRANCH` and fill in the username and password for you.
    * Add an `.htaccess` file to the production server to deny access temporarily.
    * Remove all files in the project root directory on the production server.
    * Use `scp` to copy all local files to the production server.
    * Load SQL queries in `runonce.sql` on the production server.
    * Remove the `.htaccess` file on the production server.

## Requirement ##

Your development environment should have:

+ Unix-like System
+ SSH Client
+ Git Client
+ Java Runtime Environment

Your production server should have:

+ Unix-like System
+ Apache Server
+ MySQL Server
+ PHP Interpreter
+ SSH Server

## License ##

The MIT License

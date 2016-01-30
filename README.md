# LAMP Deployment Tool #

Automatically Deploy LAMP-Based Web Services

## Getting Started ##

You can install **LAMP Deployment Tool** by executing the following command:

```bash
./install
```

> You may need to replace the shells with your current one in the installation script.

Download and install [the JCE files][1] if you are encountering the following error message:

```bash
java.security.InvalidKeyException: Illegal key size or default parameters
```

## Usage ##

1. Switch to your project root directory by `cd /path/to/my/proj/`.

2. Run `dep init` to setup a new configuration.

    * Check if directory `.git` exists.
    * Prompt you to input the information of your Git repository and production server.
    * AES encryption for your passwords based on another independent password.
    * Store your settings into `.depcfg` and add the file to `.gitignore`.

3. Run `dep` to deploy your project.

    * Check if file `.depcfg` exists.
    * Add `.depcfg` to `.gitignore` if it has not been added.
    * Commit all changes and prompt you to fill in the commit message.
    * Push to the default branch of your remote repository.
    * Add an `.htaccess` file to the production server to deny access temporarily.
    * Remove all files in the project root directory on the production server.
    * Use SCP to copy all local files to the production server.
    * If file `runonce.sql` exists, load the SQL queries in the file.
    * Remove the `.htaccess` file on the production server.

## Customize ##

You can easily extend **LAMP Deployment Tool** by implementing the Action interface.  
After that, use the following commands to build:

```bash
./gradlew build
./gradlew upload
```

## Requirement ##

+ Your development environment should have:

    * Java Runtime Environment

> All the functionality is written in pure Java, so it is unnecessary to install anything else.

+ Your production server should have:

    * MySQL Server
    * SSH Server

> Despite the name, **LAMP Deployment Tool** should be compatible with many other server platforms.

## License ##

Most of the self-made codes are released under [the MIT license][2].  
However, **LAMP Deployment Tool** also adopt several open-source libraries.  
For those libraries, all rights belong to their respective owners.

[1]: http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html
[2]: https://github.com/yuwen41200/lamp-deploy/raw/master/LICENSE

/**
 * InitAction - The Initialization Action Class
 * Called by command `dep init`.
 */

import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;
import javax.xml.bind.*;
import org.json.*;

public class InitAction implements Action {
	private String GIT_USR;
	private String GIT_PWD;
	private String GIT_IV;
	private String GIT_SALT;
	private String GIT_BRANCH;
	private String SRV_USR;
	private String SRV_PWD;
	private String SRV_IV;
	private String SRV_SALT;
	private String SRV_IP;
	private String SRV_PATH;
	private String SQL_USR;
	private String SQL_PWD;
	private String SQL_IV;
	private String SQL_SALT;
	private String SQL_IP;
	private String SQL_DB;

	@Override
	public void getStatus() {
		Path gitPath = Paths.get(System.getProperty("user.dir") + File.separator + ".git");
		if (Files.notExists(gitPath, LinkOption.NOFOLLOW_LINKS)) {
			System.err.println("Invalid Git repository.");
			System.exit(1);
		}
	}

	@Override
	public void run() {
		Scanner scanner = new Scanner(System.in);
		String pwd = null;
		BlockCipher blockCipher;
		BlockCipher.BlockCipherData blockCipherData;
		System.out.println("Setting up for your Git repository.");
		System.out.print("Username: ");
		if (scanner.hasNextLine())
			GIT_USR = scanner.nextLine();
		System.out.print("Password: ");
		if (scanner.hasNextLine())
			GIT_PWD = scanner.nextLine();
		System.out.print("Default Branch: ");
		if (scanner.hasNextLine())
			GIT_BRANCH = scanner.nextLine();
		System.out.println("Setting up for your HTTP(S) server.");
		System.out.print("Username: ");
		if (scanner.hasNextLine())
			SRV_USR = scanner.nextLine();
		System.out.print("Password: ");
		if (scanner.hasNextLine())
			SRV_PWD = scanner.nextLine();
		System.out.print("IP Address: ");
		if (scanner.hasNextLine())
			SRV_IP = scanner.nextLine();
		System.out.print("Project Root Path: ");
		if (scanner.hasNextLine())
			SRV_PATH = scanner.nextLine();
		System.out.println("Setting up for your MySQL server.");
		System.out.print("Username: ");
		if (scanner.hasNextLine())
			SQL_USR = scanner.nextLine();
		System.out.print("Password: ");
		if (scanner.hasNextLine())
			SQL_PWD = scanner.nextLine();
		System.out.print("IP Address: ");
		if (scanner.hasNextLine())
			SQL_IP = scanner.nextLine();
		System.out.print("Default Database: ");
		if (scanner.hasNextLine())
			SQL_DB = scanner.nextLine();
		System.out.println("Please enter a password.");
		System.out.println("The passwords for your git repository and production server " +
				"will be encrypted using this password.");
		System.out.println("You should remember this password because it will not be " +
				"stored in your disk.");
		if (scanner.hasNextLine())
			pwd = scanner.nextLine();
		blockCipher = new BlockCipher(pwd);
		blockCipherData = blockCipher.encrypt(GIT_PWD);
		GIT_PWD  = DatatypeConverter.printBase64Binary(blockCipherData.cipherText);
		GIT_IV   = DatatypeConverter.printBase64Binary(blockCipherData.initializationVector);
		GIT_SALT = DatatypeConverter.printBase64Binary(blockCipherData.salt);
		blockCipherData = blockCipher.encrypt(SRV_PWD);
		SRV_PWD  = DatatypeConverter.printBase64Binary(blockCipherData.cipherText);
		SRV_IV   = DatatypeConverter.printBase64Binary(blockCipherData.initializationVector);
		SRV_SALT = DatatypeConverter.printBase64Binary(blockCipherData.salt);
		blockCipherData = blockCipher.encrypt(SQL_PWD);
		SQL_PWD  = DatatypeConverter.printBase64Binary(blockCipherData.cipherText);
		SQL_IV   = DatatypeConverter.printBase64Binary(blockCipherData.initializationVector);
		SQL_SALT = DatatypeConverter.printBase64Binary(blockCipherData.salt);
	}

	// @formatter:off
	@Override
	public void setStatus() {
		String jsonString = new JSONStringer()
			.object()
				.key("GIT").object()
					.key("USR").value(GIT_USR)
					.key("PWD").array()
						.value(GIT_PWD)
						.value(GIT_IV)
						.value(GIT_SALT)
					.endArray()
					.key("BRANCH").value(GIT_BRANCH)
				.endObject()
				.key("SRV").object()
					.key("USR").value(SRV_USR)
					.key("PWD").array()
						.value(SRV_PWD)
						.value(SRV_IV)
						.value(SRV_SALT)
					.endArray()
					.key("IP").value(SRV_IP)
					.key("PATH").value(SRV_PATH)
				.endObject()
				.key("SQL").object()
					.key("USR").value(SQL_USR)
					.key("PWD").array()
						.value(SQL_PWD)
						.value(SQL_IV)
						.value(SQL_SALT)
					.endArray()
					.key("IP").value(SQL_IP)
					.key("DB").value(SQL_DB)
				.endObject()
			.endObject()
		.toString();
		Path depcfgPath = Paths.get(System.getProperty("user.dir") + File.separator + ".depcfg");
		try {
			Files.write(
				depcfgPath,
				jsonString.getBytes(Charset.forName("UTF-8")),
				StandardOpenOption.CREATE,
				StandardOpenOption.TRUNCATE_EXISTING
			);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		String ignoreString = "\n# LAMP Deployment Tool Configuration File\n.depcfg\n";
		Path gitignorePath = Paths.get(System.getProperty("user.dir") + File.separator + ".gitignore");
		try {
			Files.write(
				gitignorePath,
				ignoreString.getBytes(Charset.forName("UTF-8")),
				StandardOpenOption.CREATE,
				StandardOpenOption.APPEND
			);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	// @formatter:on
}

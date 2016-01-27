/**
 * InitAction - The Initialization Action Class
 * Called by command `dep init`.
 */

import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;
import org.json.*;

public class InitAction implements Action {
	private String GIT_USR;
	private String GIT_PWD;
	private String GIT_BRANCH;
	private String SRV_USR;
	private String SRV_PWD;
	private String SRV_IP;
	private String SRV_PATH;

	@Override
	public void getStatus() {
		Path gitPath = Paths.get(System.getProperty("user.dir") + "/.git");
		if (Files.notExists(gitPath, LinkOption.NOFOLLOW_LINKS)) {
			System.err.println("INVALID_GIT_REPOSITORY");
			System.exit(1);
		}
	}

	@Override
	public void run() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Setting up for your git repository.");
		System.out.print("Username: ");
		if (scanner.hasNextLine())
			GIT_USR = scanner.nextLine();
		System.out.print("Password: ");
		if (scanner.hasNextLine())
			GIT_PWD = scanner.nextLine();
		System.out.print("Default Branch: ");
		if (scanner.hasNextLine())
			GIT_BRANCH = scanner.nextLine();
		System.out.println("Setting up for your production server.");
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
	}

	@Override
	public void setStatus() {
		String jsonString = new JSONStringer()
			.object()
				.key("GIT_USR")
				.value(GIT_USR)
				.key("GIT_PWD")
				.value(GIT_PWD)
				.key("GIT_BRANCH")
				.value(GIT_BRANCH)
				.key("SRV_USR")
				.value(SRV_USR)
				.key("GRV_PWD")
				.value(SRV_PWD)
				.key("SRV_IP")
				.value(SRV_IP)
				.key("SRV_PATH")
				.value(SRV_PATH)
			.endObject()
		.toString();
		Path depcfgPath = Paths.get(System.getProperty("user.dir") + "/.depcfg");
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
		Path gitignorePath = Paths.get(System.getProperty("user.dir") + "/.gitignore");
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
}

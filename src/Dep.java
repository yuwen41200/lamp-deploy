import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;
import org.json.*;

public class Dep {
	private String GIT_USR;
	private String GIT_PWD;
	private String GIT_BRANCH;
	private String SRV_USR;
	private String SRV_PWD;
	private String SRV_IP;
	private String SRV_PATH;

	public static void main(String args[]) {
		Dep dep = new Dep();
		dep.init();
	}

	public void init() {
		Path gitPath = Paths.get(".git");
		if (Files.notExists(gitPath, LinkOption.NOFOLLOW_LINKS)) {
			System.err.println("INVALID_GIT_REPOSITORY");
			System.exit(1);
		}
		Scanner scanner = new Scanner(System.in);
		System.out.println("Please input the username for your git repository:");
		if (scanner.hasNextLine()) GIT_USR = scanner.nextLine();
		System.out.println("Please input the password for your git repository:");
		if (scanner.hasNextLine()) GIT_PWD = scanner.nextLine();
		System.out.println("Please input the default branch for your git repository:");
		if (scanner.hasNextLine()) GIT_BRANCH = scanner.nextLine();
		System.out.println("Please input the username for your production server:");
		if (scanner.hasNextLine()) SRV_USR = scanner.nextLine();
		System.out.println("Please input the password for your production server:");
		if (scanner.hasNextLine()) SRV_PWD = scanner.nextLine();
		System.out.println("Please input the IP address for your production server:");
		if (scanner.hasNextLine()) SRV_IP = scanner.nextLine();
		System.out.println("Please input the project root path on your production server:");
		if (scanner.hasNextLine()) SRV_PATH = scanner.nextLine();
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
		Path depcfgPath = Paths.get(".depcfg");
		try {
			Files.write(
				depcfgPath,
				jsonString.getBytes(Charset.forName("UTF-8")),
				StandardOpenOption.TRUNCATE_EXISTING
			);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		String ignoreString = "\n# LAMP Deployment Tool Configuration File\n.depcfg\n";
		Path gitignorePath = Paths.get(".gitignore");
		try {
			Files.write(
				gitignorePath,
				ignoreString.getBytes(Charset.forName("UTF-8")),
				StandardOpenOption.APPEND
			);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}

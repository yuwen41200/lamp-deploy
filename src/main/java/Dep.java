/**
 * Dep - The Program Entry Point
 * Execute actions according to the provided arguments.
 */

import java.lang.reflect.*;

public class Dep {
	public static void main(String[] args) {
		if (args.length == 0) {
			try {
				Dep.exec(new GitAction());
				System.out.println("\u001b[1;32mAction `git` has been successfully completed.\u001b[0m");
				Dep.exec(new SrvAction());
				System.out.println("\u001b[1;32mAction `srv` has been successfully completed.\u001b[0m");
				System.exit(0);
			}
			catch (NotAnActionException e) {
				System.err.println("The program is broken.");
				System.exit(1);
			}
		}
		for (String arg: args) {
			try {
				String name = arg.substring(0, 1).toUpperCase() + arg.substring(1) + "Action";
				Class<?> found = Class.forName(name);
				Constructor<?> constructor = found.getConstructor();
				Object instance = constructor.newInstance();
				Dep.exec(instance);
				System.out.println("\u001b[1;32mAction `" + arg + "` has been successfully " +
						"completed.\u001b[0m");
			}
			catch (ClassNotFoundException e) {
				System.err.println("Action `" + arg + "` does not exist.");
				System.exit(1);
			}
			catch (NoSuchMethodException | InstantiationException
					| IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
			catch (NotAnActionException e) {
				System.err.println("Action `" + arg + "` is invalid.");
				System.exit(1);
			}
		}
	}

	public static void exec(Object object) throws NotAnActionException {
		if (object instanceof Action) {
			Action action = (Action) object;
			action.getStatus();
			action.run();
			action.setStatus();
		}
		else {
			throw new NotAnActionException("NOT_AN_ACTION");
		}
	}
}

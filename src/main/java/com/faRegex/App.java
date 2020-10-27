package com.faRegex;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.faRegex.core.ComportamentalFANetwork;
import com.faRegex.userInputOutput.UserInputReader;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class App {
	static class ArgStructure {
		@Parameter
		private String comportamentalFANetwork;

		@Parameter(names = { "-h", "--help" }, description = "Print this help", help = true)
		private boolean help;

		@Parameter(names = { "-f", "--file" }, description = "File containing the ComportamentalFANetwork")
		private String file;

		@Parameter(names = { "-o", "--out-dir" }, description = "Directory to output results", required = true)
		private String outDir;

		@Parameter(names = { "-t",
				"--task" }, description = "The task to accomplish. \n\t\t1 - Compute the Comportamental FA Network Space \n\t\t2 - Compute the CFANS relative to an observation"
				, required = true)
		private int task;
	}

	public static void main(String[] args) {
		StringBuilder fromStdIn = null;
		ArgStructure arguments = new ArgStructure();
		JCommander command = JCommander.newBuilder().addObject(arguments).build();
		ComportamentalFANetwork cfaNetwork = null;
		String outFile = null;

		try {
			command.parse(args);
			outFile = arguments.outDir.replaceFirst("^~", System.getProperty("user.home"));
			if (arguments.help) {
				command.usage();
				System.exit(0);
			} else if (arguments.comportamentalFANetwork != null && arguments.file != null) {
				System.err.println("The use of -f option and string unassociated with -like option is forbidden");
				throw new ParameterException("");
			} else if (arguments.comportamentalFANetwork == null && arguments.file == null) {
				fromStdIn = new StringBuilder();

				try (Scanner scanner = new Scanner(System.in)) {
					if (System.in.available() > 0)
						scanner.forEachRemaining(fromStdIn::append);
				} catch (IOException e) {
					System.err.println("The standard input is not available");
					throw new ParameterException("");
				}
				if (fromStdIn.toString().isEmpty()) {
					System.err.println(
							"Use one between -f option or string unassociated with -like option is mandatory or input redirection");
					throw new ParameterException("");
				}
			} else if (Files.exists(Paths.get(outFile))) {
				System.err.println("The file already exists");
				System.exit(1);
			} else if (!Files.isWritable(Paths.get(outFile.substring(0, outFile.lastIndexOf("/"))))) {
				System.err.println("Cannot access the output directory");
				System.exit(1);
			}
		} catch (ParameterException e) {
			System.err.println("Options not correctly formatted");
			command.usage();
			System.exit(1);
		}

		try {
			if (fromStdIn != null)
				cfaNetwork = new UserInputReader().readInput(fromStdIn.toString());
			else
				cfaNetwork = new UserInputReader()
						.readInput(arguments.file != null ? arguments.file : arguments.comportamentalFANetwork);
		} catch (JsonMappingException e) {
			System.err.println("The input doesn't correspond to a ComportamentalFANetwork");
			System.exit(1);
		} catch (JsonProcessingException e) {
			System.err.println("The input doesn't correspond to a json file");
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Some error with file handling occurred");
			System.exit(1);
		}

		if (!cfaNetwork.check()) {
			System.err.println("The input describe a malformatted ComportamentalFANetwork");
			System.exit(1);
		}

		// From here the ComportamentalFANetwork must be intended as correct
		try {
			switch (arguments.task) {
				case 1:
					execute(new ComportamentalFANSpace(cfaNetwork), outFile);
					break;
				case 2:
					execute(new ComportamentalFANSObservation(cfaNetwork), outFile);
					break;
				default:
					throw new IllegalArgumentException("The option " + arguments.task + " in not valid");
			}
		} catch (IOException e) {
			System.err.println("Cannot write " + Paths.get(outFile).toString());
			System.exit(1);
		} catch(IllegalArgumentException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
		System.exit(0);
	}

	private static void execute(Task task, String outFile) throws IOException {
		task.build();

		new UserInputReader().writeResult(task, Paths.get(outFile));
	}
}

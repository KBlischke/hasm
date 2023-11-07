import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        String usage = new String(
            "Usage: java -jar <programName>.jar <inputFileName>.asm [<outputFileName>.hack]"
        );
        File input = getInput(args, usage);
        if (input == null) {
            System.exit(1);
        }
        File output = getOutput(args, usage);

        SymbolTable symbolTable = getLabels(input);

        Parser parser = new Parser(input);
        ArrayList<String> translation = new ArrayList<>();
        int variable = 16;

        while (parser.hasMoreInstructions()) {
            switch (parser.getInstructionType()) {
                case "A_INSTRUCTION":
                    String address = parser.getSymbol();

                    if (Coder.getAddress(address) != null) {
                        translation.add("0" + Coder.getAddress(address) + "\n");
                    }
                    else if (symbolTable.contains(address)) {
                        translation.add(String.format(
                            "0%s\n",
                            Coder.getAddress(symbolTable.getAddress(address)))
                        );
                    }
                    else {
                        translation.add(String.format(
                            "0%s\n",
                            Coder.getAddress(String.format("%d", variable)))
                        );
                        symbolTable.addEntry(
                            address,
                            String.format("%d", variable)
                        );
                        ++variable;
                    }
                    break;

                case "C_INSTRUCTION":
                    String comp = Coder.getComp(parser.getComp());
                    String dest = Coder.getDest(parser.getDest());
                    String jump = Coder.getJump(parser.getJump());
                    translation.add(String.format("111%s%s%s\n", comp, dest, jump));
                    break;

                case "LABEL":
                    break;

                case "EMPTY":
                    break;
            }

            parser.advance();
        }

        writeFile(translation, output);
    }

    /**
     * <p>
     * Returns an assembly file from the command-line arguments to translate.
     * </p>
     * 
     * @param  args  The command-line arguments
     * @param  usage A default message to display in case of wrong usage
     * @return An assembly file from the command-line arguments to translate
     * 
     * @see    java.io.File
     */
    private static File getInput(String[] args, String usage) {
        File input;

        switch (args.length) {
            case 0:
                System.out.printf("No arguments werde provided\n%s\n", usage);
                input = null;
                break;

            case 1:
                ;

            case 2:
                input = new File(args[0]);

                if (!input.exists()) {
                    System.out.println("The input file doesn't exist");
                    input = null;
                }
                else if (!args[0].endsWith(".asm")) {
                    System.out.printf("The input file isn't an assembly file\n%s\n", usage);
                    input = null;
                }
                break;

            default:
                System.out.printf("To many arguments were provided\n%s\n", usage);
                input = null;
                break;
        }
        return input;
    }

    /**
     * <p>
     * Returns a Hack machine instruction file to write to.
     * </p>
     * 
     * @param  args  The command-line arguments
     * @param  usage A default message to display in case of wrong usage
     * @return A Hack machine instruction file to write to
     * 
     * @see    java.io.File
     */
    private static File getOutput(String[] args, String usage) {
        File output;

        switch (args.length) {
            case 1:
                output = new File(args[0].replace(".asm", ".hack"));
                break;

            case 2:
                output = new File(args[1]);

                if (!args[1].endsWith(".hack")) {
                    System.out.printf("The output file isn't a Hack file\n%s\n", usage);
                }
                break;

            default:
                output = null;
        }
        return output;
    }

    /**
     * <p>
     * Returns a {@link SymbolTable} object with labels from an assembly file added to it.
     * </p>
     * 
     * @param  input The assembly file from which labels should be added
     * @return A {@link SymbolTable} object with labels from an assembly file added to it
     * 
     * @see    java.io.File
     */
    private static SymbolTable getLabels(File input) {
        SymbolTable symbolTable = new SymbolTable();
        Parser parser = new Parser(input);
        int noInstruction = 0;

        while (parser.hasMoreInstructions()) {
            try {
                switch (parser.getInstructionType()) {
                    case "LABEL":
                        symbolTable.addEntry(
                            parser.getSymbol(),
                            String.format("%s", parser.getCurrentLine() - noInstruction)
                        );
                        ++noInstruction;
                        break;
                    case "EMPTY":
                        ++noInstruction;
                        break;
                }
            }
            catch (NullPointerException e) {
                final String ANSI_RED = "\u001B[31m";
                final String ANSI_RESET = "\u001B[0m";

                System.out.println(ANSI_RED + "Translation aborted!" + ANSI_RESET);
                System.out.printf(
                    "Error at line %d:\n'%s' isn't a valid Hack assembly instruction",
                    parser.getCurrentLine() + 1,
                    parser.getCurrentInstruction()
                );
                System.exit(2);
            }
            parser.advance();
        }
        return symbolTable;
    }

    /**
     * <p>
     * Writes lines to an output file.
     * </p>
     * 
     * @param  lines The lines to write to a file
     * @param  file  The file to write to
     * 
     * @see    java.util.ArryList
     * @see    java.lang.String
     * @see    java.io.File
     */
    private static void writeFile(ArrayList<String> lines, File file) {
        try {
            file.createNewFile();
            FileWriter writer = new FileWriter(file);

            for (String line : lines) {
                writer.write(line);
            }

            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
            System.exit(3);
        }
    }
}

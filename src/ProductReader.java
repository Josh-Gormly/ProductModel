import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import static java.nio.file.StandardOpenOption.CREATE;
import java.util.ArrayList;
import javax.swing.JFileChooser;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class ProductReader
{
    public static void main(String[] args)
    {

        JFileChooser chooser = new JFileChooser();
        File selectedFile;
        String rec = "";
        ArrayList<String> products = new ArrayList<>();
        final int FIELDS_LENGTH = 4;

        String id, name, description;
        double cost;

        try
        {

            File workingDirectory = new File(System.getProperty("user.dir"));

            chooser.setCurrentDirectory(workingDirectory);

            if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
            {
                selectedFile = chooser.getSelectedFile();
                Path file = selectedFile.toPath();

                InputStream in =
                        new BufferedInputStream(Files.newInputStream(file, CREATE));
                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(in));
                int line = 0;
                while(reader.ready())
                {
                    rec = reader.readLine();
                    products.add(rec);
                    line++;

                    System.out.printf("\nLine %4d %-60s ", line, rec);
                }
                reader.close();
                System.out.println("\n\nData file read!");



                System.out.println(String.format("%-7s %-9s %-24s %5s", "ID", "Name", "Description", "Cost"));
                System.out.println("=====================================================");

                String[] fields;
                for(String l:products)
                {
                    fields = l.split(","); // Split the record into the fields

                    if(fields.length == FIELDS_LENGTH)
                    {
                        id          = fields[0].trim();
                        name        = fields[1].trim();
                        description = fields[2].trim();
                        cost        = Double.parseDouble(fields[3].trim());

                        System.out.printf("\n%-8s%-10s%-25s%6s", id, name, description, cost);
                    }
                    else
                    {
                            System.out.println("Found a record that may be corrupt: ");
                            System.out.println(l);
                    }
                }

            }
            else
            {
                System.out.println("Failed to choose a file to process");
                System.out.println("Run the program again!");
                System.exit(0);
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found!!!");
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

}

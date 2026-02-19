import java.io.*;
import java.nio.charset.StandardCharsets;

public class Main {

    public static void main(String[] args) {

        try {

            ProcessBuilder pb = new ProcessBuilder(
                    "python",
                    "-X",
                    "utf8",
                    "crawler.py"
            );


            // 프로젝트 루트를 작업 디렉토리로 설정
            pb.directory(new File(System.getProperty("user.dir")));

            pb.redirectErrorStream(true);

            Process process = pb.start();

            BufferedReader reader =
                    new BufferedReader(
                            new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8)
                    );

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            process.waitFor();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package cn.linshenkx.halosyncserver;

import cn.linshenkx.halosyncserver.utils.HaloUtils;
import com.sshtools.common.publickey.SshKeyPairGenerator;
import com.sshtools.common.publickey.SshKeyUtils;
import com.sshtools.common.ssh.SshException;
import com.sshtools.common.ssh.components.SshKeyPair;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DatetimeTest {
    @Test
    public void createKeyPair() throws IOException, SshException {
        SshKeyPair pair = SshKeyPairGenerator.generateKeyPair(SshKeyPairGenerator.SSH2_RSA, 2048);
        SshKeyUtils.createPublicKeyFile(pair.getPublicKey(),
                "Generated by Maverick Synergy", new File("key.pub"));
        SshKeyUtils.createPrivateKeyFile(pair, "123456", new File("key"));
    }

    @Test
    public void testRegex() {
        System.out.println(replacePostLink("{% post_link xxx.md xxx %} hello world {% post_link xxx2.md xxx2 %}"));
    }

    private String replacePostLink(String markdown) {
        if (StringUtils.isBlank(markdown)) return markdown;

        try{
            String pattern = "\\{% post_link (.+?) %}";

            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(markdown);

            while(m.find()) {
                String match = m.group();
                String content = m.group(1);
                String[] split = content.trim().split(" ");
                String fileMd5 = HaloUtils.toMd5("source/_post/"+split[0]+".md");
                String filename = split[1];
                String replace = "[%s](%s)".formatted(filename, "${halo-sync.halo.url}/archive/"+fileMd5);
                markdown = markdown.replace(match, replace);
            }
        }catch (Exception e) {
            return markdown;
        }

        return markdown;
    }
}

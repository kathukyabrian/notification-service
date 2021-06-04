package tech.kitucode.notification.util;

public class TemplateUtil {
    public static String sendSpecialMessage(String owner, String message) {
        return "<!doctype html>" +
                "<html>" +
                "<head> <meta name=\"viewport\" content=\"width=device-width\"> <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"> <title>Activation Code</title> " +
                "<style>#outer-table{background-color: #f1f3f4; font-family: CenturyGothicStd, \"Century Gothic\", CenturyGothic, AppleGothic, sans-serif !important; line-height: 20px; color: #202124; width: 100%;}#content-data{width: 500px; margin: auto; background-color: white; padding: 20px; border-radius: 8px;}#logo-table{width: 100%;}.ext-link{color: white !important; background-color: #18224B; padding: 10px 20px; text-decoration: none; border-radius: 50px; text-transform: uppercase; font-size: small; font-weight: 600; letter-spacing: 1px;}#footer{color: #80868B; font-size: small;}</style>" +
                "</head>" +
                "<body> <table id=\"outer-table\"> <tbody> <th> <td> <p>&nbsp;</p></td></th> <tr> <td colspan=\"1\"></td><td colspan=\"1\" id=\"content-data\"> <table id=\"logo-table\"> <tbody> <tr> <td colspan=\"1\"> Comtra </td><td align=\"right\" colspan=\"1\"> </td></tr></tbody> </table> <hr/> " +
                "<h3> Hello " + owner + ". </h3> " +
                "<p>"+message+" </p><br/> " +
                "<p> <i><strong>NOTE:</strong>Brian cares about you.</strong></i> </p><p><br/> </p><hr/> <p id=\"footer\" align=center> Brian Kitunda, +254740272915</p></td><td colspan=\"1\"></td></tr><th> <td> <p>&nbsp;</p></td></th> </tbody> </table></body></html>";
    }
}

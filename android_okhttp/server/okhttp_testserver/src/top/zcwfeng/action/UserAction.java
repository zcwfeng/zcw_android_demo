package top.zcwfeng.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class UserAction extends ActionSupport {


    public File mPhoto;
    public String mPhotoFileName;
    public String mPhotoContentType;
    public String login() throws IOException {
        HttpServletRequest request = ServletActionContext.getRequest();
        System.out.println("sessionId：" + request.getSession().getId());

        System.out.println("Username;" + username+":password:" + password);
        HttpServletResponse response = ServletActionContext.getResponse();

        PrintWriter writer  =  response.getWriter();
        writer.write("login success");
        return null;
    }

    public String postFile() throws IOException {
        HttpServletRequest request = ServletActionContext.getRequest();
        System.out.println("sessionId：" + request.getSession().getId());


        String dir = ServletActionContext.getServletContext().getRealPath("files");
        File file = new File(dir,"exception.png");
        FileOutputStream fout = new FileOutputStream(file);
        ServletInputStream in = request.getInputStream();
        StringBuilder sb = new StringBuilder();
        int len =0;
        byte[] buf = new byte[1024];
        while((len = in.read(buf))!= -1){
            fout.write(buf,0,len);
        }
        fout.flush();
        fout.close();
        return null;
    }

    public String uploadInfo() throws IOException {
        System.out.println("Username;" + username+":password:"+password);

        if(mPhoto == null) {
            System.out.println(mPhotoFileName + ":is null");
        }

        String dir = ServletActionContext.getServletContext().getRealPath("files");
        File file = new File(dir,mPhotoFileName);
        FileUtils.copyFile(mPhoto,file);
        return null;
    }

    public String postString() throws IOException {
        HttpServletRequest request = ServletActionContext.getRequest();
        System.out.println("sessionId：" + request.getRequestedSessionId());

        ServletInputStream in = request.getInputStream();
        StringBuilder sb = new StringBuilder();
        int len =0;
        byte[] buf = new byte[1024];
        while((len = in.read(buf))!= -1){
            sb.append(new String(buf,0,len));
        }
        System.out.println(sb.toString());
        return null;
    }

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String password;

}



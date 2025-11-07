
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SessionExpiredFilter implements Filter {





    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestURI = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();

        boolean isAjax = "partial/ajax".equals(httpRequest.getHeader("Faces-Request"));

        if (requestURI.endsWith("/start.xhtml")) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = httpRequest.getSession(false);
        boolean sessionExpired = (session == null);

        if (sessionExpired) {
            handleSessionExpired(httpRequest, httpResponse, contextPath, isAjax);
            return;
        }

        chain.doFilter(request, response);
    }


    private void handleSessionExpired(HttpServletRequest request,
                                      HttpServletResponse response,
                                      String contextPath,
                                      boolean isAjax) throws IOException {

        if (isAjax) {
            response.setContentType("text/xml");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Cache-Control", "no-cache");

            String redirectUrl = contextPath + "/start.xhtml?sessionExpired=true";
            String xmlResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                    "<partial-response>" +
                    "<redirect url=\"" + redirectUrl + "\"></redirect>" +
                    "</partial-response>";

            response.getWriter().write(xmlResponse);
            response.setStatus(200);
        } else {
            String redirectUrl = contextPath + "/start.xhtml?sessionExpired=true";
            response.sendRedirect(redirectUrl);
        }
    }

}
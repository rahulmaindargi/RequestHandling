package rahul.api.pdf_generation.logging.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Component
public class Log4j2LoggingFilter extends OncePerRequestFilter {
    protected Logger logger = LogManager.getLogger(getClass());

    private boolean includeResponsePayload = true;
    private int maxPayloadLength = 1000;

    private String getContentAsString(byte[] buf, int maxLength, String charsetName) {
        if (buf == null || buf.length == 0) return "";
        int length = Math.min(buf.length, this.maxPayloadLength);
        try {
            return new String(buf, 0, length, charsetName);
        } catch (UnsupportedEncodingException ex) {
            return "Unsupported Encoding";
        }
    }

    /**
     * Log each request and respponse with full Request URI, content payload and duration of the request in ms.
     *
     * @param request     the request
     * @param response    the response
     * @param filterChain chain of filters
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        long startTime = System.currentTimeMillis();
        StringBuilder reqInfo = new StringBuilder()
                .append("[")
                .append(startTime % 10000)  // request ID
                .append("] ")
                .append(request.getMethod())
                .append(" ")
                .append(request.getRequestURL());

        String queryString = request.getQueryString();
        if (queryString != null) {
            reqInfo.append("?").append(queryString);
        }

        if (request.getAuthType() != null) {
            reqInfo.append(", authType=")
                    .append(request.getAuthType());
        }
        if (request.getUserPrincipal() != null) {
            reqInfo.append(", principalName=")
                    .append(request.getUserPrincipal().getName());
        }

        logger.debug("=> " + reqInfo);

        // ========= Log request and response payload ("body") ========
        // We CANNOT simply read the request payload here, because then the InputStream would be consumed and cannot be read again by the actual processing/server.
        //    String reqBody = DoogiesUtil._stream2String(request.getInputStream());   // THIS WOULD NOT WORK!
        // So we need to apply some stronger magic here :-)
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        filterChain.doFilter(wrappedRequest, wrappedResponse);     // ======== This performs the actual request!
        long duration = System.currentTimeMillis() - startTime;

        // I can only log the request's body AFTER the request has been made and ContentCachingRequestWrapper did its work.
        String requestBody = this.getContentAsString(wrappedRequest.getContentAsByteArray(), this.maxPayloadLength, request.getCharacterEncoding());
        if (requestBody.length() > 0) {
            logger.debug("   Request body:\n" + requestBody);
        }

        logger.debug("<= " + reqInfo + ": returned status=" + response.getStatus() + " in " + duration + "ms");
        if (includeResponsePayload) {
            byte[] buf = wrappedResponse.getContentAsByteArray();
            logger.debug("   Response body:\n" + getContentAsString(buf, this.maxPayloadLength, response.getCharacterEncoding()));
        }

        wrappedResponse.copyBodyToResponse();  // IMPORTANT: copy content of response back into original response

    }

}

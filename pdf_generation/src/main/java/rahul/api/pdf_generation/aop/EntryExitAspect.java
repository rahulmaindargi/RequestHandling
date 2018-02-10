package rahul.api.pdf_generation.aop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ObjectArrayMessage;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class EntryExitAspect {

    @Around("execution(* rahul.api.pdf_generation.*.*(..)) && !execution(* rahul.api.pdf_generation.logging.*.*(..))")
    public Object aroundMethod(ProceedingJoinPoint pjp) throws Throwable {
        Logger log = LogManager.getLogger(pjp.getTarget().getClass());

        if (log.isTraceEnabled()) {
            return executeWithTrace(pjp, log);
        }
        return pjp.proceed();
    }

    private Object executeWithTrace(ProceedingJoinPoint pjp, Logger logger) throws Throwable {
        ObjectMapper mapper = new ObjectMapper();
              Object result = null;
        try {
            logger.traceEntry(pjp::toShortString, () -> {
                try {
                    return mapper.writeValueAsString(pjp.getArgs());
                } catch (JsonProcessingException e) {
                    return pjp.getArgs();
                }
            });
            result = pjp.proceed();
            return result;
        } catch (Throwable throwable) {
            logger.error(String.format("#### - Error while executing method '%s' : %s ", pjp.toShortString(), new ObjectArrayMessage(pjp.getArgs())), throwable);
            throw throwable;
        } finally {
            if (logger.isTraceEnabled()) {
                try {
                  logger.traceExit(mapper.writeValueAsString(result));
                } catch (JsonProcessingException e) {
                    logger.traceExit(result);
                }
                //   logger.traceExit(this);
            }
        }
    }

}

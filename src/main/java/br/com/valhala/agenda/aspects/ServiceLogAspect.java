package br.com.valhala.agenda.aspects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
public class ServiceLogAspect {

	private static final Logger LOG = LogManager.getLogger(ServiceLogAspect.class);

	@Around("execution (* br.com.valhala.agenda.service.*.*(..))")
	public Object logaExecucaoMetodo(ProceedingJoinPoint pjp) throws Throwable {
		
		MethodSignature signature = (MethodSignature)pjp.getSignature();
		LOG.info("Executando metodo: " + signature.getName() + " da classe: " + signature.getDeclaringType().getSimpleName());
		try {
			Object resultado = pjp.proceed();
			return resultado;
		} catch (Throwable e) {
			LOG.error("Ocorreu erro.", e);
			throw e;
		}

	}

}

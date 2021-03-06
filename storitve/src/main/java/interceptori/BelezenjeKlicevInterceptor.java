package interceptori;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
@Interceptor
@BeleziKlice

public class BelezenjeKlicevInterceptor {

    @Inject
    private BelezenjeKlicevZrno bZrno;
    @AroundInvoke

    public Object novKlic(InvocationContext context) throws Exception {
        bZrno.sobiraj();
        return context.proceed();
    }
}

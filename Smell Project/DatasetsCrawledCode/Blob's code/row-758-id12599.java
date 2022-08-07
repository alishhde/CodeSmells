@SuppressWarnings("rawtypes")
public interface FlowableRxInvoker extends RxInvoker<Flowable> {


 @Override
 Flowable<Response> get();


 @Override
    <T> Flowable<T> get(Class<T> responseType);


 @Override
    <T> Flowable<T> get(GenericType<T> responseType);


 @Override
 Flowable<Response> put(Entity<?> entity);


 @Override
    <T> Flowable<T> put(Entity<?> entity, Class<T> clazz);


 @Override
    <T> Flowable<T> put(Entity<?> entity, GenericType<T> type);


 @Override
 Flowable<Response> post(Entity<?> entity);


 @Override
    <T> Flowable<T> post(Entity<?> entity, Class<T> clazz);


 @Override
    <T> Flowable<T> post(Entity<?> entity, GenericType<T> type);


 @Override
 Flowable<Response> delete();


 @Override
    <T> Flowable<T> delete(Class<T> responseType);


 @Override
    <T> Flowable<T> delete(GenericType<T> responseType);


 @Override
 Flowable<Response> head();


 @Override
 Flowable<Response> options();


 @Override
    <T> Flowable<T> options(Class<T> responseType);


 @Override
    <T> Flowable<T> options(GenericType<T> responseType);


 @Override
 Flowable<Response> trace();


 @Override
    <T> Flowable<T> trace(Class<T> responseType);


 @Override
    <T> Flowable<T> trace(GenericType<T> responseType);


 @Override
 Flowable<Response> method(String name);


 @Override
    <T> Flowable<T> method(String name, Class<T> responseType);


 @Override
    <T> Flowable<T> method(String name, GenericType<T> responseType);


 @Override
 Flowable<Response> method(String name, Entity<?> entity);


 @Override
    <T> Flowable<T> method(String name, Entity<?> entity, Class<T> responseType);


 @Override
    <T> Flowable<T> method(String name, Entity<?> entity, GenericType<T> responseType);
}
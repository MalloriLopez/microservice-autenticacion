package co.com.bancolombia.r2dbc;

import co.com.bancolombia.model.user.User;
import co.com.bancolombia.model.user.gateways.UserRepository;
import co.com.bancolombia.r2dbc.entities.UserEntity;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

@Repository
public class MyReactiveRepositoryAdapter extends ReactiveAdapterOperations<
    User,
    UserEntity,
    String,
    MyReactiveRepository
> implements UserRepository
{
    public MyReactiveRepositoryAdapter(MyReactiveRepository repository, ObjectMapper mapper) {
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(repository, mapper, UserEntity -> mapper.map(UserEntity, User.class));
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return repository.deleteByUserId(id);
    }

    @Transactional
    @Override
    public Mono<User> save(User u) {
        return super.save(u);
    }

    @Override
    public Mono<User> findById(String id) {
        return super.findById(id);
    }

    @Override
    public Flux<User> findAll(){
        return super.findAll();
    }

    @Override
    public Mono<Boolean> existsByEmail(String email) {
     return repository.existsByEmail(email);
    }


}

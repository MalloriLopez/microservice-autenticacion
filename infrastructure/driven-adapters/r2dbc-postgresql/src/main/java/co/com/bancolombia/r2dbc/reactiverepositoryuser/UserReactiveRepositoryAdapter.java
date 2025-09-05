package co.com.bancolombia.r2dbc.reactiverepositoryuser;

import co.com.bancolombia.model.user.User;
import co.com.bancolombia.model.user.gateways.UserRepository;
import co.com.bancolombia.r2dbc.entities.UserEntity;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserReactiveRepositoryAdapter extends ReactiveAdapterOperations<
    User,
    UserEntity,
    String,
        UserReactiveRepository
> implements UserRepository
{
    public UserReactiveRepositoryAdapter(UserReactiveRepository repository, ObjectMapper mapper) {
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(repository, mapper, UserEntity -> mapper.map(UserEntity, User.class));
    }

    @Transactional
    @Override
    public Mono<User> save(User u) {
        return super.save(u);
    }


    @Override
    public Mono<Boolean> existsByEmail(String email) {
     return repository.existsByEmail(email);
    }


}

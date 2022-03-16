package uz.pdp.pdp_food_delivery.rest.service.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import uz.pdp.pdp_food_delivery.rest.config.security.utils.JwtUtils;
import uz.pdp.pdp_food_delivery.rest.dto.auth.AuthUserCreateDto;
import uz.pdp.pdp_food_delivery.rest.dto.auth.AuthUserDto;
import uz.pdp.pdp_food_delivery.rest.dto.auth.AuthUserUpdateDto;
import uz.pdp.pdp_food_delivery.rest.entity.AuthUser;
import uz.pdp.pdp_food_delivery.rest.mapper.auth.AuthUserMapper;
import uz.pdp.pdp_food_delivery.rest.repository.auth.AuthUserRepository;
import uz.pdp.pdp_food_delivery.rest.service.base.AbstractService;
import uz.pdp.pdp_food_delivery.rest.service.base.GenericCrudService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
@Slf4j
@Service
public class AuthUserService extends AbstractService<AuthUserMapper, AuthUserRepository> implements   GenericCrudService<
        AuthUser,
        AuthUserDto,
        AuthUserCreateDto,
        AuthUserUpdateDto
        >,UserDetailsService{

    public AuthUserService(AuthUserMapper mapper, AuthUserRepository repository) {
        super(mapper, repository);
    }

    @Override
    public void delete(Long id) {
        Optional<AuthUser> byId = repository.findByIdAndDeleted(id, false);
        if (byId.isPresent()) {
            AuthUser authUser = byId.get();
            authUser.setDeleted(true);
            repository.save(authUser);
        } else
            throw new RuntimeException("User not found");
    }

    @Override
    public void update(AuthUserUpdateDto authUserUpdateDto) {
        String email = authUserUpdateDto.getEmail();
        Long id = authUserUpdateDto.getId();
        String password = authUserUpdateDto.getPassword();
        String phoneNumber = authUserUpdateDto.getPhoneNumber();
        String fullName = authUserUpdateDto.getFullName();

        Optional<AuthUser> byId = repository.findByIdAndDeleted(id, false);
        if (byId.isPresent()) {
            AuthUser authUser = byId.get();
            authUser.setEmail(email);
            authUser.setPassword(password);
            authUser.setPhoneNumber(phoneNumber);
            authUser.setFullName(fullName);
            repository.save(authUser);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public Long create(AuthUserCreateDto authUserCreateDto) {
        AuthUser authUser = mapper.fromCreateDto(authUserCreateDto);
        AuthUser save = repository.save(authUser);
        return save.getId();
    }

    @Override
    public List<AuthUserDto> getAll() {
        return null;
    }

    @Override
    public AuthUserDto get(Long id) {
        Optional<AuthUser> byIdAndDeleted = repository.findByIdAndDeleted(id, false);
        if (byIdAndDeleted.isPresent()) {
            AuthUser authUser = byIdAndDeleted.get();
            return mapper.toDto(authUser);
        } else {
            throw new RuntimeException("User not found");
        }
    }


    public List<AuthUserDto> getGivenPage(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AuthUser> all = repository.findAllByDeleted(false, pageable);
        List<AuthUser> content = all.getContent();
        return mapper.toDto(content);
    }

    public Long create(AuthUserCreateDto dto, Long userId) {
        AuthUser authUser = mapper.fromCreateDto(dto);
        authUser.setCreatedBy(userId);
        AuthUser save = repository.save(authUser);
        return save.getId();
    }

    public String getLanguage(String chatId) {
        return repository.getLanguage(chatId);
    }

    public String findRoleByChatId(String chatId) {
        return repository.findRoleByChatId(chatId);
    }


    public void getRefreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // gets "Authentication" header from request
        String authorizationHeader = request.getHeader(AUTHORIZATION);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                //token
                String refresh_token = authorizationHeader.substring("Bearer ".length());

                //jwt algorithm
                Algorithm algorithm = JwtUtils.getAlgorithm();

                //checks token to valid  if not valid throws exception
                JWTVerifier verifier = JWT.require(algorithm).build();

                //if valid decode it with given algorithm
                DecodedJWT decodedJWT = verifier.verify(refresh_token);

                // from payload part gets subject
                String phoneNumber = decodedJWT.getSubject();

                // gets phone from db
                AuthUser user = getUserByUsername(phoneNumber);

                //creates new access token
                String access_token = JWT.create()
                        .withSubject(user.getPhoneNumber())
                        .withExpiresAt(JwtUtils.getExpiry())
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", Collections.singletonList(user.getRole().name()))
                        .sign(algorithm);

                //puts into map given refresh and created access tokens
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);

                //sets response feedbackType
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);

                new ObjectMapper().writeValue(response.getOutputStream(), tokens);

            } catch (Exception exception) {
                response.setHeader("error", exception.getMessage());
                response.setStatus(HttpStatus.FORBIDDEN.value());
                //response.sendError(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }

    public AuthUser getUserByUsername(String phone) {
        log.info("Getting user by phone : {}", phone);
        return repository.findByPhoneNumber(phone);
    }

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        AuthUser user = repository.findByPhoneNumber(phone);
        return User.builder()
                .username(user.getPhoneNumber())
                .password(user.getPassword())
                .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name())))
                .build();
    }

    public AuthUserDto getByChatId(String chatId) {
        AuthUser authUser = repository.findByChatId(chatId);
        return mapper.toDto(authUser);
    }
}

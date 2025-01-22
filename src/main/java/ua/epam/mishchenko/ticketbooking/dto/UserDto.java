package ua.epam.mishchenko.ticketbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import ua.epam.mishchenko.ticketbooking.model.User;
import ua.epam.mishchenko.ticketbooking.model.mongo.UserMongo;

import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @Id
    private String id;

    private String name;
    private String email;

    private UserAccountDTO userAccount;

    public UserDto(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public UserDto(Long id, String name, String email) {
        this.id = String.valueOf(id);
        this.name = name;
        this.email = email;
    }

    public static UserDto fromUserToUserDto(User sqlUser) {
        UserDto userDto = new UserDto();
        userDto.setId(String.valueOf(sqlUser.getId()));
        userDto.setName(sqlUser.getName());
        userDto.setEmail(sqlUser.getEmail());
        return userDto;
    }

    public static UserDto fromUserMongoToUserDto(UserMongo userMongo) {
        UserDto userDto = new UserDto();
        userDto.setId(userMongo.getId());
        userDto.setName(userMongo.getName());
        userDto.setEmail(userMongo.getEmail());
        return userDto;
    }

    public static User fromUserDtotoSqlUser(UserDto userDto) {
        User user = new User();
        Long id = Optional.ofNullable(userDto)
                .map(UserDto::getId)
                .map(Long::parseLong)
                .orElse(null);
        user.setId(id);
        user.setEmail(userDto.getEmail());
        user.setName(userDto.getName());
        return user;
    }

    public static UserMongo fromUserDtotoUserMongo(UserDto userDto) {
        UserMongo user = new UserMongo();
        user.setId(user.getId());
        user.setEmail(userDto.getEmail());
        user.setName(userDto.getName());
        return user;
    }

}

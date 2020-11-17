package com.readingisgood.readingisgood.account.controller;

import com.readingisgood.readingisgood.account.model.*;
import com.readingisgood.readingisgood.account.service.AccountService;
import com.readingisgood.readingisgood.applicationuser.model.ApplicationUserDto;
import com.readingisgood.readingisgood.base.controller.BaseController;
import com.readingisgood.readingisgood.base.exception.UnAuthorizedException;
import com.readingisgood.readingisgood.security.model.UserLogin;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;


@RestController
public class AccountController extends BaseController {

    public static final String PATH_POST_REFRESH = "/account/token/refresh";
    public static final String PATH_POST_LOGIN = "/account/login";
    public static final String PATH_POST_SIGN_UP = "/account/register";
    private static final String PATH_DELETE_LOGOUT = "/account/logout";
    public static final String ACCOUNT_RESET_PASSWORD = "/account/resetPassword";
    public static final String ACCOUNT_VALIDATE_TOKEN = "/account/validateToken";
    public static final String ACCOUNT_VALIDATE_ACCESS_TOKEN = "/account/validateAccessToken";
    public static final String ACCOUNT_SAVE_PASSWORD = "/account/savePassword";

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping(path = AccountController.PATH_POST_SIGN_UP, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create user",
            notes = "This method creates a new user",
            response = TokenPair.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!") })
    public ResponseEntity registerUser(@Valid @RequestBody ApplicationUserDto applicationUserDto) {
        TokenPair tokenPair = accountService.register(applicationUserDto);
        return new ResponseEntity<>(getGenericApiResponse(tokenPair), HttpStatus.CREATED);
    }

    @RequestMapping(path = PATH_POST_LOGIN, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Login user",
            notes = "Login user",
            response = TokenPair.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!") })
    public ResponseEntity tokenPostLogin(@Valid @RequestBody UserLogin userLogin) {
        return new ResponseEntity<>(getGenericApiResponse(accountService.loginUser(userLogin)), HttpStatus.OK);
    }

    @RequestMapping(path = PATH_POST_REFRESH, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Refresh token",
            notes = "Refresh token",
            response = AccessToken.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!") })
    public ResponseEntity tokenPostRefresh(@Valid @RequestBody RefreshToken refreshToken) {

        Optional<AccessToken> accessToken = accountService.refreshAccessToken(refreshToken.getRefreshToken());
        if (accessToken.isEmpty()){
            throw new UnAuthorizedException("Invalid refresh token.");
        }
        return new ResponseEntity<>(getGenericApiResponse(accessToken.get()), HttpStatus.OK);
    }

    @RequestMapping(path = PATH_DELETE_LOGOUT, method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Logout user",
            notes = "Logout user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!") })
    public ResponseEntity tokenDeleteLogout(@Valid @RequestBody RefreshToken refreshToken) {
        accountService.logoutUser(refreshToken.getRefreshToken());
        return ResponseEntity.ok("Başarılı");
    }

}

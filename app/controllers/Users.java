package controllers;

import java.util.Date;
import java.util.Random;

import models.Bracelet;
import models.Sms;
import models.User;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import play.data.Form;
import play.db.ebean.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import utils.Pagination;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.node.ObjectNode;

import constants.Constants;
import constants.Messages;
import forms.UserForm;
import utils.SmsUtils;

public class Users extends Controller {

    final static Logger logger = LoggerFactory.getLogger(Users.class);

    public static Result findAll() {
        ObjectNode result = Json.newObject();
        try {
            Pagination pagination = Form.form(Pagination.class).bindFromRequest().get();
            pagination = User.findAll(pagination);
            result.put(Constants.CODE, Constants.SUCCESS);
            result.put(Constants.DATAS, Json.toJson(pagination));
        } catch (Exception e) {
            result.put(Constants.CODE, Constants.ERROR);
            result.put(Constants.MESSAGE, Messages.QUERY_ERROR);

        }
        return ok(result);
    }

    public static Result findById(Long id) {
        ObjectNode result = Json.newObject();
        try {
            User user = User.findById(id);
            result.put(Constants.CODE, Constants.SUCCESS);
            result.put(Constants.DATAS, Json.toJson(user));
        } catch (Exception e) {
            result.put(Constants.CODE, Constants.ERROR);
            result.put(Constants.MESSAGE, Messages.QUERY_ERROR);

        }
        return ok(result);
    }

    @Transactional
    public static Result store() {
        ObjectNode result = Json.newObject();
        try {
            UserForm form = Form.form(UserForm.class).bindFromRequest().get();
            if (form.getId() != null && form.getId() > 0) {
                User dbUser = User.findById(form.getId());
                dbUser.setRealname(form.getRealname());
                dbUser.setUserType(form.getUserType());
                dbUser.setStatus(form.getStatus());
                dbUser.setGender(form.getGender());
                dbUser.setBirthday(form.getBirthday());
                dbUser.setWeight(form.getWeight());
                dbUser.setHeight(form.getHeight());
                dbUser.setModifiedDate(new Date());
                dbUser.setModifiedBy(session(Constants.CURRENT_USERID));
                User.store(dbUser);
            } else {
                User newUser = new User();
                BeanUtils.copyProperties(form, newUser);
                newUser.setCreateDate(new Date());
                newUser.setCreateBy(session(Constants.CURRENT_USERID));
                User.store(newUser);
            }
            result.put(Constants.CODE, Constants.SUCCESS);
            result.put(Constants.MESSAGE, "Store User Successfully.");
        } catch (Exception e) {
            logger.error("[store] -> [exception]", e);
            result.put(Constants.CODE, Constants.ERROR);
            result.put(Constants.MESSAGE, Messages.QUERY_ERROR);
        }
        return ok(result);
    }

    public static Result register() {
        ObjectNode result = Json.newObject();
        try {
            UserForm form = Form.form(UserForm.class).bindFromRequest().get();
            if (StringUtils.isNotEmpty(form.getUsername())) {
                User dbUser = User.findByUsername(form.getUsername());
                if (dbUser == null) {
                    User.register(form);
                    result.put(Constants.CODE, Constants.SUCCESS);
                    result.put(Constants.MESSAGE, "Register User Successfully.");
                } else {
                    result.put(Constants.CODE, Constants.FAILURE);
                    result.put(Constants.MESSAGE, "The Username is exist, please input a new one.");
                }
            }
        } catch (Exception e) {
            logger.error("[register] -> [exception]", e);
            result.put(Constants.CODE, Constants.ERROR);
            result.put(Constants.MESSAGE, "Register User Error Happened.");

        }
        return ok(result);
    }

    public static Result updateProfile() {
        ObjectNode result = Json.newObject();
        try {
            UserForm form = Form.form(UserForm.class).bindFromRequest().get();
            if (StringUtils.isNotEmpty(form.getUsername())) {
                User dbUser = User.findByUsername(form.getUsername());
                if (dbUser == null) {
                    result.put(Constants.CODE, Constants.FAILURE);
                    result.put(Constants.MESSAGE, "The user doesn't exist.");
                } else {
                    dbUser.setGender(form.getGender());
                    dbUser.setBirthday(form.getBirthday());
                    dbUser.setWeight(form.getWeight());
                    dbUser.setHeight(form.getHeight());
                    dbUser.setModifiedDate(new Date());
                    dbUser.setModifiedBy(form.getUsername());
                    User.store(dbUser);
                    result.put(Constants.CODE, Constants.SUCCESS);
                    result.put(Constants.MESSAGE, "Update successfully.");
                }
            }
        } catch (Exception e) {
            logger.error("[register] -> [exception]", e);
            result.put(Constants.CODE, Constants.ERROR);
            result.put(Constants.MESSAGE, "Update Profile User Error Happened.");

        }
        return ok(result);
    }


    public static Result checkStatus() {
        ObjectNode result = Json.newObject();
        try {
            UserForm form = Form.form(UserForm.class).bindFromRequest().get();
            if (StringUtils.isNotEmpty(form.getUsername()) && StringUtils.isNotEmpty(form.getMobile())) {
                Boolean isRegister = User.isRegister(form);
                if (!isRegister) {
                    Bracelet bracelet = Bracelet.findByPassCode(form.getPassCode());
                    if (bracelet != null) {
                        User.pending(form, bracelet);
                        result.put(Constants.CODE, Constants.SUCCESS);
                        result.put(Constants.MESSAGE, "The account is available.");
                        Integer code = 1000 + new Random().nextInt(9000);
                        Sms.store(form.getMobile(), code);
                        SmsUtils.sendMsg(form.getMobile(), "Please input the validation code: " + code);
                    } else {
                        result.put(Constants.CODE, Constants.FAILURE);
                        result.put(Constants.MESSAGE, "The bracelet has been activated already.");
                    }
                } else {
                    result.put(Constants.CODE, Constants.FAILURE);
                    result.put(Constants.MESSAGE, "The Mobile or Username is exist, please input a new one.");
                }
            }
        } catch (Exception e) {
            logger.error("[register] -> [exception]", e);
            result.put(Constants.CODE, Constants.ERROR);
            result.put(Constants.MESSAGE, "Register User Error Happened.");

        }
        return ok(result);
    }

    public static Result checkValidationCode() {
        ObjectNode result = Json.newObject();
        try {
            UserForm form = Form.form(UserForm.class).bindFromRequest().get();
            if (StringUtils.isNotEmpty(form.getMobile())) {
                Sms sms = Sms.findByMobile(form.getMobile());
                if (sms != null && sms.status && sms.code.intValue() == form.getSmsCode().intValue()) {
                    Sms.disable(sms);
                    result.put(Constants.CODE, Constants.SUCCESS);
                    result.put(Constants.MESSAGE, "Activated successfull.");
                } else {
                    result.put(Constants.CODE, Constants.FAILURE);
                    result.put(Constants.MESSAGE, "The validation code is not correct.");
                }
            }
        } catch (Exception e) {
            logger.error("[register] -> [exception]", e);
            result.put(Constants.CODE, Constants.ERROR);
            result.put(Constants.MESSAGE, "Register User Error Happened.");
        }
        return ok(result);
    }

    public static Result completeRegister() {
        ObjectNode result = Json.newObject();
        try {
            UserForm form = Form.form(UserForm.class).bindFromRequest().get();
            if (StringUtils.isNotEmpty(form.getUsername())) {
                User dbUser = User.findByUsername(form.getUsername());
                if (dbUser == null) {
                    result.put(Constants.CODE, Constants.FAILURE);
                    result.put(Constants.MESSAGE, "The user doesn't exist.");
                } else {
                    User.update(form);
                    result.put(Constants.CODE, Constants.SUCCESS);
                    result.put(Constants.MESSAGE, "Completed registration successfully.");
                }
            }
        } catch (Exception e) {
            logger.error("[register] -> [exception]", e);
            result.put(Constants.CODE, Constants.ERROR);
            result.put(Constants.MESSAGE, "Completed Registration Error Happened.");

        }
        return ok(result);
    }

}

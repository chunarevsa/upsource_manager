package com.rtkit.upsource_manager.controllers

import com.rtkit.upsource_manager.services.AuthServiceImpl
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping

@Controller
class AuthController(private val authService: AuthServiceImpl) {

    @GetMapping
    fun validateCon(model: MutableMap<String, Any>): String {
        if (!authService.validateAuthFile()) {
            return "redirect:/login"
        }
        return if (authService.validateCon()) "redirect:/main/review" else "redirect:/login"
    }

    @GetMapping("/login")
    fun login(model: MutableMap<String, Any>): String {
        return "login"
    }

    @PostMapping("/login")
    fun getLogin(
            model: MutableMap<String, Any>,
            username: String,
            password: String,
    ): String {
        return if (authService.login(username, password)) "redirect:/main/review" else "redirect:/login"
    }

    @PostMapping("/logout")
    fun logout(model: MutableMap<String, Any>): String {
        authService.logout()
        return "redirect:/login"
    }
}
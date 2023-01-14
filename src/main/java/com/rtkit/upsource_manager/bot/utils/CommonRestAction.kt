package com.rtkit.upsource_manager.bot.utils

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.requests.RestAction
import net.dv8tion.jda.api.requests.RestFuture
import java.util.concurrent.CompletableFuture
import java.util.function.BooleanSupplier
import java.util.function.Consumer

/** @author Johnson on 20.02.2021*/
@Deprecated("async")
class CommonRestAction<T>(private val jda: JDA, private val consumer: (onComplete: (value: T) -> Unit) -> Unit) :
    RestAction<T> {
    private var check = false;

    override fun getJDA(): JDA = jda

    override fun setCheck(checks: BooleanSupplier?): RestAction<T> {
        check = checks?.asBoolean ?: false
        return this
    }

    override fun queue(success: Consumer<in T>?, failure: Consumer<in Throwable>?) {
        consumer.invoke({ success?.accept(it) })
    }

    override fun complete(shouldQueue: Boolean): T {
        var res: T? = null
        consumer.invoke({ res = it })
        return res!!
    }

    override fun submit(shouldQueue: Boolean): CompletableFuture<T> {
        return RestFuture<T>(complete())
    }
}
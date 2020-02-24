package com.cubivue.inlogic.di

import javax.inject.Scope

import kotlin.annotation.Retention
import kotlin.annotation.Target
import kotlin.annotation.AnnotationRetention

/**
 * The FragmentScoped custom scoping annotation specifies that the lifespan of a dependency be
 * the same as that of a Fragment. This is used to annotate dependencies that behave like a
 * singleton within the lifespan of a Fragment
 */
@Scope
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.TYPE, AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class FragmentScoped

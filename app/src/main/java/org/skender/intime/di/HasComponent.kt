package org.skender.intime.di

interface HasComponent<C> {
    fun getComponent(): C
}
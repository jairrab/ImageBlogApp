package com.jairrab.myapp.models

sealed class UserAction {
    class ViewImage(val post: Post) : UserAction()
    class ViewUser(val post: Post) : UserAction()
    class ChangePublicVisibility(val post: Post) : UserAction()
    class DeletePost(val post: Post) : UserAction()
}
package com.jairrab.myapp.view.user

import com.jairrab.myapp.models.Post

sealed class UserAction {
    class ChangePublicVisibility(val post: Post) : UserAction()
    class DeletePost(val post: Post) : UserAction()
}
package nestedApplyResources

class NestedApplyResource {
    val test =  1.apply { val test = 2.apply { this + 1 } }
    val test2 = 2.apply { this + 1 }
}

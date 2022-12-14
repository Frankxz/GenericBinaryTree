package org.labwork.kotlin.part.kotlinpartmodule.Tree

class KotlinNode(data: Any, i: Int, nothing: KotlinNode?) {
    // PARENT AND CHILDS
   private var parent: KotlinNode? = null
   private var left: KotlinNode? = null
   private var right: KotlinNode? = null

    // STORAGE DATA
    private var data: Any

    // BALANCE COEFFICIENTS
    private var balanceCoeffiecient = 0

    init {
        parent = nothing
        this.data = data
        balanceCoeffiecient = i
    }
    fun getParent(): KotlinNode? {
        return parent
    }

    fun setParent(parent: KotlinNode?) {
        this.parent = parent
    }

    fun getLeft(): KotlinNode? {
        return left
    }

    fun setLeft(left: KotlinNode?) {
        this.left = left
    }

    fun getRight(): KotlinNode? {
        return right
    }

    fun setRight(right: KotlinNode?) {
        this.right = right
    }

    fun getData(): Any? {
        return data
    }

    fun getBalanceCoeffiecient(): Int {
        return balanceCoeffiecient
    }

    fun setBalanceCoeffiecient(balanceCoeffiecient: Int) {
        this.balanceCoeffiecient = balanceCoeffiecient
    }

    fun incrementBalanceCoeffiecient() {
        balanceCoeffiecient++
    }

    fun decrementBalanceCoeffiecient() {
        balanceCoeffiecient--
    }

    companion object {
        val EH: Int
            get() {
              return 0
            }
        val RH: Int
            get() {
                return -1
            }
        val LH: Int
            get() {
                return 1
            }
    }

}
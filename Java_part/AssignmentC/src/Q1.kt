import java.util.*

/*
括号检验：输入一个代数表达式，表达式中只能含有+,-,*,/,(,),1,2,3,4,5,6,7,8,9,0字符且每个数字均小于10，
假设表达式中除了括号匹配有错误外，再无其它错误。编写算法对输入的表达式进行检验，判断括号匹配是否正确。
*/
fun main(args: Array<String>) {
    val s = Scanner(System.`in`)
    val input = s.nextLine()
    val parentheses = Stack<Int>()
    try {
        for ((i, c) in input.withIndex()) {
            when (c) {
                '(' -> parentheses += i
                ')' -> parentheses.pop()
            }
        }
        if (!parentheses.empty())
            throw EmptyStackException()
        println("正确")
    } catch (e: EmptyStackException) {
        println("不正确")
    }
}

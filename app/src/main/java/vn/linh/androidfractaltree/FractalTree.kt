package vn.linh.androidfractaltree

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class FractalTree @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    View(context, attrs, defStyleAttr) {

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val width = 1000f
        val height = 1000f

        val root = Branch(PointF(width / 2f, height), PointF(width / 2f, height - 200f))
        root.draw(canvas)

        var topBranch = HashSet<Branch>()
        topBranch.add(root)

        for (i in 0 until 9) {
            val newTopBranch = HashSet<Branch>()
            for (topB in topBranch) {
                val branchA = topB.branchA()
                val branchB = topB.branchB()

                newTopBranch.add(branchA)
                newTopBranch.add(branchB)

                branchA.draw(canvas)
                branchB.draw(canvas)
            }
            topBranch = newTopBranch
        }
    }

    class Branch(val start: PointF, val end: PointF) {
        private val digt = 0.25f

        private var paint = Paint().apply {
            color = Color.RED
            strokeWidth = 6f
        }

        fun draw(canvas: Canvas) {
            canvas.drawLine(start.x, start.y, end.x, end.y, paint)
        }

        fun branchA(): Branch {
            val mat = Matrix()
            mat.postRotate(225f, end.x, end.y)
            val newEndPoint = floatArrayOf(lerp(start.x, end.x, digt), lerp(start.y, end.y, digt))
            mat.mapPoints(newEndPoint)
            return Branch(end, PointF(newEndPoint[0], newEndPoint[1]))
        }

        fun branchB(): Branch {
            val mat = Matrix()
            mat.postRotate(135f, end.x, end.y)
            val newEndPoint = floatArrayOf(lerp(start.x, end.x, digt), lerp(start.y, end.y, digt))
            mat.mapPoints(newEndPoint)
            return Branch(end, PointF(newEndPoint[0], newEndPoint[1]))
        }

        private fun lerp(point1: Float, point2: Float, dist: Float): Float {
            return point1 + dist * (point2 - point1)
        }
    }
}

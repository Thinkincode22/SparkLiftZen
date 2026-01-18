
package com.example.spark_uplift_zen

data class Quote(val text: String, val author: String)

fun getQuotes(): List<Quote> {
    return listOf(
        Quote("The only way to do great work is to love what you do.", "Steve Jobs"),
        Quote("Innovation distinguishes between a leader and a follower.", "Steve Jobs"),
        Quote("Your time is limited, so don’t waste it living someone else’s life.", "Steve Jobs"),
        Quote("The future belongs to those who believe in the beauty of their dreams.", "Eleanor Roosevelt"),
        Quote("It is during our darkest moments that we must focus to see the light.", "Aristotle"),
        Quote("Whoever is happy will make others happy too.", "Anne Frank"),
        Quote("Do not go where the path may lead, go instead where there is no path and leave a trail.", "Ralph Waldo Emerson"),
        Quote("You will face many defeats in life, but never let yourself be defeated.", "Maya Angelou"),
        Quote("The greatest glory in living lies not in never falling, but in rising every time we fall.", "Nelson Mandela"),
        Quote("Life is what happens when you're busy making other plans.", "John Lennon")
    )
}

package com.bookora.app.data.repository

import com.bookora.app.data.model.Book
import com.bookora.app.data.model.Category

object BookRepository {

    val categories = listOf(
        Category(1, "Fiction"),
        Category(2, "Non-Fiction"),
        Category(3, "Self-Help"),
        Category(4, "Business"),
        Category(5, "Biography"),
        Category(6, "Children")
    )

    val topPicks = listOf(
        Book(
            id = 1,
            title = "Atomic Habits",
            author = "James Clear",
            price = 499,
            originalPrice = 699,
            rating = 4.7f,
            coverUrl = "https://covers.openlibrary.org/b/id/10527843-L.jpg",
            category = "Self-Help",
            description = "An easy and proven way to build good habits and break bad ones. Tiny changes lead to remarkable results."
        ),
        Book(
            id = 2,
            title = "The Alchemist",
            author = "Paulo Coelho",
            price = 299,
            originalPrice = 399,
            rating = 4.6f,
            coverUrl = "https://covers.openlibrary.org/b/id/8325025-L.jpg",
            category = "Fiction",
            description = "A magical story about following your dreams and listening to your heart."
        ),
        Book(
            id = 3,
            title = "Ikigai",
            author = "Héctor García",
            price = 349,
            originalPrice = 450,
            rating = 4.5f,
            coverUrl = "https://covers.openlibrary.org/b/id/10110415-L.jpg",
            category = "Self-Help",
            description = "The Japanese secret to a long and happy life. Discover your reason for being."
        ),
        Book(
            id = 4,
            title = "Steve Jobs",
            author = "Walter Isaacson",
            price = 499,
            originalPrice = 699,
            rating = 4.8f,
            coverUrl = "https://covers.openlibrary.org/b/id/8228691-L.jpg",
            category = "Biography",
            description = "The exclusive biography of Apple's co-founder and visionary innovator."
        )
    )

    val bestSellers = listOf(
        Book(
            id = 5,
            title = "The Subtle Art of Not Giving a F*ck",
            author = "Mark Manson",
            price = 299,
            originalPrice = 399,
            rating = 4.5f,
            coverUrl = "https://covers.openlibrary.org/b/id/8739161-L.jpg",
            category = "Self-Help",
            description = "A counterintuitive approach to living a good life."
        ),
        Book(
            id = 6,
            title = "Rich Dad Poor Dad",
            author = "Robert T. Kiyosaki",
            price = 379,
            originalPrice = 499,
            rating = 4.5f,
            coverUrl = "https://covers.openlibrary.org/b/id/8739546-L.jpg",
            category = "Business",
            description = "What the rich teach their kids about money that the poor and middle class do not."
        ),
        Book(
            id = 7,
            title = "Sapiens",
            author = "Yuval Noah Harari",
            price = 399,
            originalPrice = 599,
            rating = 4.7f,
            coverUrl = "https://covers.openlibrary.org/b/id/8739649-L.jpg",
            category = "Non-Fiction",
            description = "A brief history of humankind from the Stone Age to the present."
        ),
        Book(
            id = 8,
            title = "Think Like a Monk",
            author = "Jay Shetty",
            price = 299,
            originalPrice = 399,
            rating = 4.6f,
            coverUrl = "https://covers.openlibrary.org/b/id/10434931-L.jpg",
            category = "Self-Help",
            description = "Train your mind for peace and purpose every day."
        )
    )

    val allBooks: List<Book> get() = topPicks + bestSellers
}

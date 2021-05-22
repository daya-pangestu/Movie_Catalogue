package com.daya.moviecatalogue.shared
import com.daya.moviecatalogue.R
import com.daya.moviecatalogue.data.main.movie.Movie
import com.daya.moviecatalogue.data.main.tvshow.TvShow

object DataDummy {
    fun getListMovie(): MutableList<Movie> {
       return mutableListOf(
            Movie(
                title = "A Star Is Born",
                description = "Seasoned musician Jackson Maine discovers — and falls in love with — struggling artist Ally. She has just about given up on her dream to make it big as a singer — until Jack coaxes her into the spotlight. But even as Ally's career takes off, the personal side of their relationship is breaking down, as Jack fights an ongoing battle with his own internal demons.",
                release_date = "2018-10-05",
                user_score = 75,
                year = 2018,
                image_url = R.drawable.poster_a_start_is_born.toString(),
                id = 332562
            ),
            Movie(
                title = "Alita: Battle Angel",
                description = "When Alita awakens with no memory of who she is in a future world she does not recognize, she is taken in by Ido, a compassionate doctor who realizes that somewhere in this abandoned cyborg shell is the heart and soul of a young woman with an extraordinary past.",
                release_date = "2019-02-14",
                user_score = 72,
                year = 2019,
                image_url = R.drawable.poster_alita.toString(),
                id = 399579
            ),
            Movie(
                title = "aquaman",
                description = "Once home to the most advanced civilization on Earth, Atlantis is now an underwater kingdom ruled by the power-hungry King Orm. With a vast army at his disposal, Orm plans to conquer the remaining oceanic people and then the surface world. Standing in his way is Arthur Curry, Orm's half-human, half-Atlantean brother and true heir to the throne.",
                release_date = "2018-12-21",
                user_score = 69,
                year = 2018,
                image_url = R.drawable.poster_aquaman.toString(),
                id = 297802
            ),
            Movie(
                title = "mortal engine",
                description = "Many thousands of years in the future, Earth’s cities roam the globe on huge wheels, devouring each other in a struggle for ever diminishing resources. On one of these massive traction cities, the old London, Tom Natsworthy has an unexpected encounter with a mysterious young woman from the wastelands who will change the course of his life forever.",
                release_date = "2018-12-14",
                user_score = 61,
                year = 2018,
                image_url = R.drawable.poster_mortal_engines.toString(),
                id = 428078
            ),
            Movie(
                title = "glass",
                description = "In a series of escalating encounters, former security guard David Dunn uses his supernatural abilities to track Kevin Wendell Crumb, a disturbed man who has twenty-four personalities. Meanwhile, the shadowy presence of Elijah Price emerges as an orchestrator who holds secrets critical to both men.",
                release_date = "2019-01-18",
                user_score = 67,
                year = 2019,
                image_url = R.drawable.poster_glass.toString(),
                id = 450465
            ),
            Movie(
                title = "Cold Pursuit",
                description = "The quiet family life of Nels Coxman, a snowplow driver, is upended after his son's murder. Nels begins a vengeful hunt for Viking, the drug lord he holds responsible for the killing, eliminating Viking's associates one by one. As Nels draws closer to Viking, his actions bring even more unexpected and violent consequences, as he proves that revenge is all in the execution.",
                release_date = "2019-02-08",
                user_score = 57,
                year = 2019,
                image_url = R.drawable.poster_cold_persuit.toString(),
                id = 438650
            ),
            Movie(
                title = "Serenity",
                description = "The quiet life of Baker Dill, a fishing boat captain who lives on the isolated Plymouth Island, where he spends his days obsessed with capturing an elusive tuna while fighting his personal demons, is interrupted when someone from his past comes to him searching for help.",
                release_date = "2019-01-25",
                user_score = 54,
                year = 2019,
                image_url = R.drawable.poster_serenity.toString(),
                id = 452832
            ),
            Movie(
                title = "Avengers: Infinity War",
                description = "As the Avengers and their allies have continued to protect the world from threats too large for any one hero to handle, a new danger has emerged from the cosmic shadows: Thanos. A despot of intergalactic infamy, his goal is to collect all six Infinity Stones, artifacts of unimaginable power, and use them to inflict his twisted will on all of reality. Everything the Avengers have fought for has led up to this moment - the fate of Earth and existence itself has never been more uncertain.",
                release_date = "2018-04-27",
                user_score = 83,
                year = 2018,
                image_url = R.drawable.poster_infinity_war.toString(),
                id = 299536
            ),
            Movie(
                title = "Bohemian Rhapsody",
                description = "Singer Freddie Mercury, guitarist Brian May, drummer Roger Taylor and bass guitarist John Deacon take the music world by storm when they form the rock 'n' roll band Queen in 1970. Hit songs become instant classics. When Mercury's increasingly wild lifestyle starts to spiral out of control, Queen soon faces its greatest challenge yet – finding a way to keep the band together amid the success and excess.",
                release_date = "2018-11-02",
                user_score = 80,
                year = 2018,
                image_url = R.drawable.poster_bohemian.toString(),
                id = 424694
            ),
            Movie(
                title = "Robin Hood",
                description = "A war-hardened Crusader and his Moorish commander mount an audacious revolt against the corrupt English crown.",
                release_date = "2018-11-21",
                user_score = 59,
                year = 2018,
                image_url = R.drawable.poster_robin_hood.toString(),
                id = 375588
            )
        )
    }

    fun getListTvShow(): MutableList<TvShow> {
        return mutableListOf(
            TvShow(
                title = "Arrow",
                year = 2012,
                description = "Spoiled billionaire playboy Oliver Queen is missing and presumed dead when his yacht is lost at sea. He returns five years later a changed man, determined to clean up the city as a hooded vigilante armed with a bow.",
                user_score = 66,
                image_url = R.drawable.poster_arrow.toString(),
                id = 1412
            ),
            TvShow(
                title = "Doom Patrol",
                year = 2019,
                description = "The Doom Patrol’s members each suffered horrible accidents that gave them superhuman abilities — but also left them scarred and disfigured. Traumatized and downtrodden, the team found purpose through The Chief, who brought them together to investigate the weirdest phenomena in existence — and to protect Earth from what they find.",
                user_score = 76,
                image_url = R.drawable.poster_doom_patrol.toString(),
                id = 79501
            ),
            TvShow(
                title = "Fairy Tail",
                year = 2009,
                description = "Lucy is a 17-year-old girl, who wants to be a full-fledged mage. One day when visiting Harujion Town, she meets Natsu, a young man who gets sick easily by any type of transportation. But Natsu isn't just any ordinary kid, he's a member of one of the world's most infamous mage guilds: Fairy Tail.",
                user_score = 78,
                image_url = R.drawable.poster_fairytail.toString(),
                id = 46261
            ),
            TvShow(
                title = "The Flash",
                year = 2014,
                description = "After a particle accelerator causes a freak storm, CSI Investigator Barry Allen is struck by lightning and falls into a coma. Months later he awakens with the power of super speed, granting him the ability to move through Central City like an unseen guardian angel. Though initially excited by his newfound powers, Barry is shocked to discover he is not the only \"meta-human\" who was created in the wake of the accelerator explosion -- and not everyone is using their new powers for good. Barry partners with S.T.A.R. Labs and dedicates his life to protect the innocent. For now, only a few close friends and associates know that Barry is literally the fastest man alive, but it won't be long before the world learns what Barry Allen has become...The Flash.",
                user_score = 77,
                image_url = R.drawable.poster_flash.toString(),
                id = 60735
            ),
            TvShow(
                title = "Riverdale",
                year = 2017,
                description = "Set in the present, the series offers a bold, subversive take on Archie, Betty, Veronica and their friends, exploring the surreality of small-town life, the darkness and weirdness bubbling beneath Riverdale’s wholesome facade.",
                user_score = 86,
                image_url = R.drawable.poster_riverdale.toString(),
                id = 69050
            ),
            TvShow(
                title = "Supernatural",
                year = 2005,
                description = "When they were boys, Sam and Dean Winchester lost their mother to a mysterious and demonic supernatural force. Subsequently, their father raised them to be soldiers. He taught them about the paranormal evil that lives in the dark corners and on the back roads of America ... and he taught them how to kill it. Now, the Winchester brothers crisscross the country in their '67 Chevy Impala, battling every kind of supernatural threat they encounter along the way.",
                user_score = 82,
                image_url = R.drawable.poster_supernatural.toString(),
                id = 1622
            ),
            TvShow(
                title = "The Walking Dead",
                year = 2010,
                description = "Sheriff's deputy Rick Grimes awakens from a coma to find a post-apocalyptic world dominated by flesh-eating zombies. He sets out to find his family and encounters many other survivors along the way.",
                user_score = 81,
                image_url = R.drawable.poster_the_walking_dead.toString(),
                id = 1402
            ),
            TvShow(
                title = "NCIS",
                year = 2003,
                description = "From murder and espionage to terrorism and stolen submarines, a team of special agents investigates any crime that has a shred of evidence connected to Navy and Marine Corps personnel, regardless of rank or position.",
                user_score = 74,
                image_url = R.drawable.poster_ncis.toString(),
                id = 4614
            ),
            TvShow(
                title = "Supergirl",
                year = 2015,
                description = "Twenty-four-year-old Kara Zor-El, who was taken in by the Danvers family when she was 13 after being sent away from Krypton, must learn to embrace her powers after previously hiding them. The Danvers teach her to be careful with her powers, until she has to reveal them during an unexpected disaster, setting her on her journey of heroism.",
                user_score = 72,
                image_url = R.drawable.poster_supergirl.toString(),
                id = 62688
            ),
            TvShow(
                title = "Gotham",
                year = 2014,
                description = "Everyone knows the name Commissioner Gordon. He is one of the crime world's greatest foes, a man whose reputation is synonymous with law and order. But what is known of Gordon's story and his rise from rookie detective to Police Commissioner? What did it take to navigate the multiple layers of corruption that secretly ruled Gotham City, the spawning ground of the world's most iconic villains? And what circumstances created them – the larger-than-life personas who would become Catwoman, The Penguin, The Riddler, Two-Face and The Joker?",
                user_score = 75,
                image_url = R.drawable.poster_gotham.toString(),
                id = 60708
            )
        )
    }
}
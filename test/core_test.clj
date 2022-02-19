(ns fen.core-test
  (:require [clojure.test :refer :all]
            [fen.core :as fen]))

(deftest start-position-fen-to-map-test
  (let [start-pos-fen "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"
        {:fen/keys [board
                    allow-white-kingside-castle?
                    allow-white-queenside-castle?
                    allow-black-kingside-castle?
                    allow-black-queenside-castle?
                    side-to-move
                    en-passant-target-square
                    halfmove-clock
                    fullmove-number]} (fen/fen->map start-pos-fen)]
    (is (= [\r \n \b \q \k \b \n \r
            \p \p \p \p \p \p \p \p
            \- \- \- \- \- \- \- \-
            \- \- \- \- \- \- \- \-
            \- \- \- \- \- \- \- \-
            \- \- \- \- \- \- \- \-
            \P \P \P \P \P \P \P \P
            \R \N \B \Q \K \B \N \R]
           board))
    (is (= :white side-to-move))
    (is (= 1 fullmove-number))
    (is (= 0 halfmove-clock))
    (is (true? allow-white-kingside-castle?))
    (is (true? allow-white-queenside-castle?))
    (is (true? allow-black-kingside-castle?))
    (is (true? allow-black-queenside-castle?))
    (is (nil? en-passant-target-square))))

(deftest start-position-map-to-fen-test
  (let [m {:fen/board [\r \n \b \q \k \b \n \r
                       \p \p \p \p \p \p \p \p
                       \- \- \- \- \- \- \- \-
                       \- \- \- \- \- \- \- \-
                       \- \- \- \- \- \- \- \-
                       \- \- \- \- \- \- \- \-
                       \P \P \P \P \P \P \P \P
                       \R \N \B \Q \K \B \N \R]
           :fen/side-to-move :white
           :fen/fullmove-number 1
           :fen/halfmove-clock 0
           :fen/allow-white-kingside-castle? true
           :fen/allow-white-queenside-castle? true
           :fen/allow-black-kingside-castle? true
           :fen/allow-black-queenside-castle? true
           :fen/en-passant-target-square nil}
        expected "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"]
    (is (= expected (fen/map->fen m)))))

(deftest after-1-e4-fen-to-map-test
  (let [after-1-e4-fen "rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1"
        {:fen/keys [board
                    allow-white-kingside-castle?
                    allow-white-queenside-castle?
                    allow-black-kingside-castle?
                    allow-black-queenside-castle?
                    side-to-move
                    en-passant-target-square
                    halfmove-clock
                    fullmove-number]} (fen/fen->map after-1-e4-fen)]
    (is (= [\r \n \b \q \k \b \n \r
            \p \p \p \p \p \p \p \p
            \- \- \- \- \- \- \- \-
            \- \- \- \- \- \- \- \-
            \- \- \- \- \P \- \- \-
            \- \- \- \- \- \- \- \-
            \P \P \P \P \- \P \P \P
            \R \N \B \Q \K \B \N \R]
           board))
    (is (= :black side-to-move))
    (is (= 1 fullmove-number))
    (is (= 0 halfmove-clock))
    (is (true? allow-white-kingside-castle?))
    (is (true? allow-white-queenside-castle?))
    (is (true? allow-black-kingside-castle?))
    (is (true? allow-black-queenside-castle?))
    (is (= "e3" en-passant-target-square))
    ))

(deftest after-1-e4-map-to-fen-test
  (let [m {:fen/board [\r \n \b \q \k \b \n \r
                       \p \p \p \p \p \p \p \p
                       \- \- \- \- \- \- \- \-
                       \- \- \- \- \- \- \- \-
                       \- \- \- \- \P \- \- \-
                       \- \- \- \- \- \- \- \-
                       \P \P \P \P \- \P \P \P
                       \R \N \B \Q \K \B \N \R]
           :fen/side-to-move :black
           :fen/fullmove-number 1
           :fen/halfmove-clock 0
           :fen/allow-white-kingside-castle? true
           :fen/allow-white-queenside-castle? true
           :fen/allow-black-kingside-castle? true
           :fen/allow-black-queenside-castle? true
           :fen/en-passant-target-square "e3"}
        expected "rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1"]
    (is (= expected (fen/map->fen m)))))

(deftest after-1-e4-c5-fen-to-map-test
  (let [after-1-e4-c5-fen "rnbqkbnr/pp1ppppp/8/2p5/4P3/8/PPPP1PPP/RNBQKBNR w KQkq c6 0 2"
        {:fen/keys [board
                    allow-white-kingside-castle?
                    allow-white-queenside-castle?
                    allow-black-kingside-castle?
                    allow-black-queenside-castle?
                    side-to-move
                    en-passant-target-square
                    halfmove-clock
                    fullmove-number]} (fen/fen->map after-1-e4-c5-fen)]
    (is (= [\r \n \b \q \k \b \n \r
            \p \p \- \p \p \p \p \p
            \- \- \- \- \- \- \- \-
            \- \- \p \- \- \- \- \-
            \- \- \- \- \P \- \- \-
            \- \- \- \- \- \- \- \-
            \P \P \P \P \- \P \P \P
            \R \N \B \Q \K \B \N \R]
           board))
    (is (= :white side-to-move))
    (is (= 2 fullmove-number))
    (is (= 0 halfmove-clock))
    (is (true? allow-white-kingside-castle?))
    (is (true? allow-white-queenside-castle?))
    (is (true? allow-black-kingside-castle?))
    (is (true? allow-black-queenside-castle?))
    (is (= "c6" en-passant-target-square))))

(deftest after-1-e4-c5-map-to-fen-test
  (let [m {:fen/board [\r \n \b \q \k \b \n \r
                       \p \p \- \p \p \p \p \p
                       \- \- \- \- \- \- \- \-
                       \- \- \p \- \- \- \- \-
                       \- \- \- \- \P \- \- \-
                       \- \- \- \- \- \- \- \-
                       \P \P \P \P \- \P \P \P
                       \R \N \B \Q \K \B \N \R]
           :fen/side-to-move :white
           :fen/fullmove-number 2
           :fen/halfmove-clock 0
           :fen/allow-white-kingside-castle? true
           :fen/allow-white-queenside-castle? true
           :fen/allow-black-kingside-castle? true
           :fen/allow-black-queenside-castle? true
           :fen/en-passant-target-square "c6"}
        expected "rnbqkbnr/pp1ppppp/8/2p5/4P3/8/PPPP1PPP/RNBQKBNR w KQkq c6 0 2"]
    (is (= expected (fen/map->fen m)))))

(deftest after-1-e4-c5-2-Nf3-fen-to-map-test
  (let [after-1-e4-c5-2-Nf3-fen "rnbqkbnr/pp1ppppp/8/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R b KQkq - 1 2"
        {:fen/keys [board
                    allow-white-kingside-castle?
                    allow-white-queenside-castle?
                    allow-black-kingside-castle?
                    allow-black-queenside-castle?
                    side-to-move
                    en-passant-target-square
                    halfmove-clock
                    fullmove-number]} (fen/fen->map after-1-e4-c5-2-Nf3-fen)]
    (is (= [\r \n \b \q \k \b \n \r
            \p \p \- \p \p \p \p \p
            \- \- \- \- \- \- \- \-
            \- \- \p \- \- \- \- \-
            \- \- \- \- \P \- \- \-
            \- \- \- \- \- \N \- \-
            \P \P \P \P \- \P \P \P
            \R \N \B \Q \K \B \- \R]
           board))
    (is (= :black side-to-move))
    (is (= 2 fullmove-number))
    (is (= 1 halfmove-clock))
    (is (true? allow-white-kingside-castle?))
    (is (true? allow-white-queenside-castle?))
    (is (true? allow-black-kingside-castle?))
    (is (true? allow-black-queenside-castle?))
    (is (nil? en-passant-target-square))))

(deftest after-1-e4-c5-2-Nf3-map-to-fen-test
  (let [m {:fen/board [\r \n \b \q \k \b \n \r
                       \p \p \- \p \p \p \p \p
                       \- \- \- \- \- \- \- \-
                       \- \- \p \- \- \- \- \-
                       \- \- \- \- \P \- \- \-
                       \- \- \- \- \- \N \- \-
                       \P \P \P \P \- \P \P \P
                       \R \N \B \Q \K \B \- \R]
           :fen/side-to-move :black
           :fen/fullmove-number 2
           :fen/halfmove-clock 1
           :fen/allow-white-kingside-castle? true
           :fen/allow-white-queenside-castle? true
           :fen/allow-black-kingside-castle? true
           :fen/allow-black-queenside-castle? true
           :fen/en-passant-target-square nil}
        expected "rnbqkbnr/pp1ppppp/8/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R b KQkq - 1 2"]
    (is (= expected (fen/map->fen m)))))

(deftest white-cant-castle-kingside-test
  (let [fen "rnbqkbnr/pppp1ppp/8/4p3/7P/8/PPPPPPPR/RNBQKBN1 b Qkq - 1 2"
        {:fen/keys [allow-white-kingside-castle?
                    allow-white-queenside-castle?
                    allow-black-kingside-castle?
                    allow-black-queenside-castle?]} (fen/fen->map fen)]
    (is (false? allow-white-kingside-castle?))
    (is (true? allow-white-queenside-castle?))
    (is (true? allow-black-kingside-castle?))
    (is (true? allow-black-queenside-castle?))))

(deftest white-cant-castle-either-side-test
  (let [fen "rnbqkbnr/pp3ppp/8/2ppp3/P6P/8/RPPPPPPR/1NBQKBN1 b kq - 1 4"
        {:fen/keys [allow-white-kingside-castle?
                    allow-white-queenside-castle?
                    allow-black-kingside-castle?
                    allow-black-queenside-castle?]} (fen/fen->map fen)]
    (is (false? allow-white-kingside-castle?))
    (is (false? allow-white-queenside-castle?))
    (is (true? allow-black-kingside-castle?))
    (is (true? allow-black-queenside-castle?))))

(deftest black-cant-castle-queenside-test
  (let [fen "1nbqkbnr/rppppppp/8/p7/3PP3/8/PPP2PPP/RNBQKBNR w KQk - 1 3"
        {:fen/keys [allow-white-kingside-castle?
                    allow-white-queenside-castle?
                    allow-black-kingside-castle?
                    allow-black-queenside-castle?]} (fen/fen->map fen)]
    (is (true? allow-white-kingside-castle?))
    (is (true? allow-white-queenside-castle?))
    (is (true? allow-black-kingside-castle?))
    (is (false? allow-black-queenside-castle?))))

(deftest two-kings-two-pawns-fen-to-map-test
  (let [fen "4k3/8/8/8/8/8/4P3/4K3 w - - 5 39"
        {:fen/keys [board
                    allow-white-kingside-castle?
                    allow-white-queenside-castle?
                    allow-black-kingside-castle?
                    allow-black-queenside-castle?
                    side-to-move
                    en-passant-target-square
                    halfmove-clock
                    fullmove-number]} (fen/fen->map fen)]
    (is (= [\- \- \- \- \k \- \- \-
            \- \- \- \- \- \- \- \-
            \- \- \- \- \- \- \- \-
            \- \- \- \- \- \- \- \-
            \- \- \- \- \- \- \- \-
            \- \- \- \- \- \- \- \-
            \- \- \- \- \P \- \- \-
            \- \- \- \- \K \- \- \-]
           board))
    (is (false? allow-white-kingside-castle?))
    (is (false? allow-white-queenside-castle?))
    (is (false? allow-black-kingside-castle?))
    (is (false? allow-black-queenside-castle?))

    (is (= :white side-to-move))
    (is (= 39 fullmove-number))
    (is (= 5 halfmove-clock))

    (is (nil? en-passant-target-square))))

(deftest two-kings-two-pawns-map-to-fen-test
  (let [m {:fen/board [\- \- \- \- \k \- \- \-
                       \- \- \- \- \- \- \- \-
                       \- \- \- \- \- \- \- \-
                       \- \- \- \- \- \- \- \-
                       \- \- \- \- \- \- \- \-
                       \- \- \- \- \- \- \- \-
                       \- \- \- \- \P \- \- \-
                       \- \- \- \- \K \- \- \-]
           :fen/side-to-move :white
           :fen/fullmove-number 39
           :fen/halfmove-clock 5
           :fen/allow-white-kingside-castle? false
           :fen/allow-white-queenside-castle? false
           :fen/allow-black-kingside-castle? false
           :fen/allow-black-queenside-castle? false
           :fen/en-passant-target-square nil}
        expected "4k3/8/8/8/8/8/4P3/4K3 w - - 5 39"]
    (is (= expected (fen/map->fen m)))))

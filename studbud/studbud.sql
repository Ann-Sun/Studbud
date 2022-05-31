-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- sql： `studbud`
--

-- --------------------------------------------------------

--
-- structure `bookmark`
--

CREATE TABLE `bookmark` (
  `id` int(11) NOT NULL,
  `address` varchar(512) NOT NULL,
  `parent_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- transfer `bookmark`
--

INSERT INTO `bookmark` (`id`, `address`, `parent_id`) VALUES
(1, 'first folder', 0),
(4, 'https://www.runoob.com/w3cnote/objective-c-tutorial.html', 1),
(17, 'xxxxxx', 0),
(18, 'yyyyy', 0),
(19, 'https://www.figma.com/file/sK8LNQpJDxdT6YYZGsx7zg/StudBud?node-id=2%3A299', 17),
(20, 'https://mail.163.com/', 18),
(21, 'https://mail.163.com/', 1),
(22, 'jjjjjj', 0),
(23, 'aaaaaaa', 22);

-- --------------------------------------------------------

--
-- structure `category`
--

CREATE TABLE `category` (
  `id` int(11) NOT NULL,
  `name` varchar(128) NOT NULL,
  `color` varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- transfer `category`
--

INSERT INTO `category` (`id`, `name`, `color`) VALUES
(1, 'first category', 'rgb(144,244,225)'),
(2, 'heiheihei', 'rgb(242,202,252)'),
(3, 'xxxxx', 'rgb(249,229,254)');

-- --------------------------------------------------------

--
-- structure `task`
--

CREATE TABLE `task` (
  `id` int(11) NOT NULL,
  `name` varchar(128) NOT NULL,
  `task_desc` text NOT NULL,
  `date` varchar(128) NOT NULL,
  `due_time` varchar(128) NOT NULL,
  `estimated_time` varchar(128) NOT NULL,
  `priority_rating` varchar(1) NOT NULL,
  `category_id` int(11) NOT NULL,
  `status` varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- transfer `task`
--

INSERT INTO `task` (`id`, `name`, `task_desc`, `date`, `due_time`, `estimated_time`, `priority_rating`, `category_id`, `status`) VALUES
(1, 'frist task', 'xxxxxxx', '1651507200000', '1652198400000', '15min', '2', 1, 'unfinished'),
(2, 'fafa', 'gggg', '', '1653300853000', '15min', '3', 2, 'unfinished'),
(3, 'ggg', 'ggg', '', '1651593600000', '15min', '3', 3, 'unfinished');

--
-- index `bookmark`
--
ALTER TABLE `bookmark`
  ADD PRIMARY KEY (`id`);

--
-- index `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`id`);

--
-- index `task`
--
ALTER TABLE `task`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT
--

--
-- AUTO_INCREMENT `bookmark`
--
ALTER TABLE `bookmark`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT `category`
--
ALTER TABLE `category`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT `task`
--
ALTER TABLE `task`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
